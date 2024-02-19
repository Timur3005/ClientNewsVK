package com.example.clientnewsvk.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.domain.CommentItem
import com.example.clientnewsvk.domain.FeedPost

class CommentsViewModel(
    feedPost: FeedPost
): ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        getComments(feedPost)
    }

    private fun getComments(feedPost: FeedPost){
        val comments = mutableListOf<CommentItem>().apply {
            repeat(10){
                add(
                    CommentItem(id = it)
                )
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }

}