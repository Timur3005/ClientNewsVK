package com.example.clientnewsvk.data.repository

import android.app.Application
import androidx.compose.ui.res.stringArrayResource
import com.example.clientnewsvk.data.Mapper
import com.example.clientnewsvk.data.network.ApiFactory
import com.example.clientnewsvk.domain.CommentItem
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.domain.StatisticType
import com.example.clientnewsvk.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class NewsFeedRepository(
    application: Application,
) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
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

    val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.deleteRecommendation(
            token = getAccessToken(),
            ownerId = feedPost.ownerId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    private val responseComments = MutableSharedFlow<FeedPost>(replay = 1)
    suspend fun responseCommentsByFeedPost(feedPost: FeedPost) {
        responseComments.emit(feedPost)
    }

    val commentsFlow = flow {
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

    suspend fun changeLikeStatus(feedPost: FeedPost) {
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
}