package com.example.clientnewsvk.presentation.comments

import com.example.clientnewsvk.domain.CommentItem
import com.example.clientnewsvk.domain.FeedPost

sealed interface CommentsScreenState {
    data object Initial: CommentsScreenState
    data class Comments(
        val post: FeedPost,
        val comments: List<CommentItem>
    ): CommentsScreenState
}