package com.example.clientnewsvk.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.repository.NewsFeedRepository
import com.example.clientnewsvk.domain.FeedPost
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application,
): ViewModel() {

    private val repository = NewsFeedRepository(application)
    val screenState = repository.commentsFlow
        .map { CommentsScreenState.Comments(post = feedPost, comments = it) as CommentsScreenState}
        .onStart { emit(CommentsScreenState.Loading) }

    init {
        getComments(feedPost)
    }

    private fun getComments(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.responseCommentsByFeedPost(feedPost)
        }
    }
}