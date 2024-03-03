package com.example.clientnewsvk.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.usecases.GetCommentsUseCase
import com.example.clientnewsvk.domain.usecases.ResponseCommentUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    feedPost: FeedPost,
    getCommentsUseCase: GetCommentsUseCase,
    private val responseCommentUseCase: ResponseCommentUseCase,
) : ViewModel() {

    val screenState = getCommentsUseCase()
        .map { CommentsScreenState.Comments(post = feedPost, comments = it) as CommentsScreenState }
        .onStart { emit(CommentsScreenState.Loading) }

    init {
        getComments(feedPost)
    }

    private fun getComments(feedPost: FeedPost) {
        viewModelScope.launch {
            responseCommentUseCase(feedPost)
        }
    }
}