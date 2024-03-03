package com.example.clientnewsvk.presentation.comments

import com.example.clientnewsvk.domain.entity.CommentItem
import com.example.clientnewsvk.domain.entity.FeedPost

sealed interface CommentsScreenState {
    data object Initial: CommentsScreenState
    data object Loading: CommentsScreenState
    data class Comments(
        val post: FeedPost,
        val comments: List<CommentItem>
    ): CommentsScreenState
}