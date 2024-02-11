package com.example.clientnewsvk.domain

sealed interface HomeScreenState {
    data object Initial: HomeScreenState
    data class Posts(val posts: List<FeedPost>): HomeScreenState
    data class Comments(val feedPost: FeedPost, val comments: List<CommentItem>): HomeScreenState
}