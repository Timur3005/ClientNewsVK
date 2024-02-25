package com.example.clientnewsvk.presentation.news

import com.example.clientnewsvk.domain.FeedPost

sealed interface FeedPostsScreenState {
    data object Initial: FeedPostsScreenState
    data class Posts(
        val posts: List<FeedPost>,
        val isDownloading: Boolean = false
    ): FeedPostsScreenState
}