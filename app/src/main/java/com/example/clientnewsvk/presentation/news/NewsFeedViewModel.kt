package com.example.clientnewsvk.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.repository.NewsFeedRepository
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val recommendationsFlow = repository.recommendations

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
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
        }
    }
}