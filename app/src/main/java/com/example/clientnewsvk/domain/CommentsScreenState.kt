package com.example.clientnewsvk.domain

sealed interface CommentsScreenState {
    data object Initial: CommentsScreenState
    data class Comments(
        val post: FeedPost,
        val comments: List<CommentItem>
    ): CommentsScreenState
}