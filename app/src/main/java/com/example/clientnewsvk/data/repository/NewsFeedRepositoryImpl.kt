package com.example.clientnewsvk.data.repository

import com.example.clientnewsvk.data.mapper.Mapper
import com.example.clientnewsvk.data.network.ApiService
import com.example.clientnewsvk.di.ApplicationScope
import com.example.clientnewsvk.presentation.main.AuthState
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.entity.StatisticItem
import com.example.clientnewsvk.domain.entity.StatisticType
import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import com.example.clientnewsvk.extentions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationScope
class NewsFeedRepositoryImpl @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val apiService: ApiService,
    private val mapper: Mapper,
) : NewsFeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val nextRecommendationsDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null
    private val loadedListFlow = flow {
        nextRecommendationsDataNeededEvents.emit(Unit)
        nextRecommendationsDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.responseRecommendedFeedPosts(token = getAccessToken())
            } else {
                apiService.responseRecommendedFeedPosts(
                    token = getAccessToken(),
                    startFrom = startFrom
                )
            }
            nextFrom = response.wallResponseDto.nextFrom
            val posts = mapper.mapWallContainerDtoToListFeedPost(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }

    override val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextRecommendationData() {
        nextRecommendationsDataNeededEvents.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.deleteRecommendation(
            token = getAccessToken(),
            ownerId = feedPost.ownerId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    private val responseComments = MutableSharedFlow<FeedPost>(replay = 1)
    override suspend fun responseCommentsByFeedPost(feedPost: FeedPost) {
        responseComments.emit(feedPost)
    }

    override val commentsFlow = flow {
        responseComments.collect { feedPost ->
            val comments = withContext(Dispatchers.IO) {
                apiService.getComments(
                    token = getAccessToken(),
                    ownerId = feedPost.ownerId,
                    postId = feedPost.id
                )
            }
            emit(mapper.mapCommentsResponseToCommentItems(comments))
        }
    }
        .retry()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.ownerId,
                itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.ownerId,
                itemId = feedPost.id
            )
        }
        val newLikesCount = response.response.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    override val authFlow = MutableStateFlow<AuthState>(AuthState.Initial)

    override suspend fun responseAuthStateFlow() {
        val realToken = token
        val wasAuth = if (realToken != null && realToken.isValid) {
            AuthState.Authorized
        } else {
            AuthState.NotAuthorized
        }
        authFlow.emit(wasAuth)
    }
}