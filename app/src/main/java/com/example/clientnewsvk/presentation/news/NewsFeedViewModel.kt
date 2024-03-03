package com.example.clientnewsvk.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.usecases.ChangeLikeStatusForFeedPostUseCase
import com.example.clientnewsvk.domain.usecases.DeletePostFromRecommendationUseCase
import com.example.clientnewsvk.domain.usecases.GetRecommendationsUseCase
import com.example.clientnewsvk.domain.usecases.LoadNextRecommendationsUseCase
import com.example.clientnewsvk.extentions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextRecommendationsUseCase: LoadNextRecommendationsUseCase,
    private val changeLikeStatusForFeedPostUseCase: ChangeLikeStatusForFeedPostUseCase,
    private val deletePostFromRecommendationUseCase: DeletePostFromRecommendationUseCase,
) : ViewModel() {

    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<FeedPostsScreenState>()

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { FeedPostsScreenState.Posts(posts = it) as FeedPostsScreenState }
        .onStart { emit(FeedPostsScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                FeedPostsScreenState.Posts(
                    posts = recommendationsFlow.value,
                    isDownloading = true
                )
            )
            loadNextRecommendationsUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            changeLikeStatusForFeedPostUseCase(feedPost)
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch {
            deletePostFromRecommendationUseCase(feedPost)
        }
    }
}