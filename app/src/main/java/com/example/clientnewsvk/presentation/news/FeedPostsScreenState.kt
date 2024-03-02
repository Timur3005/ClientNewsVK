package com.example.clientnewsvk.presentation.news

import com.example.clientnewsvk.domain.FeedPost

sealed class FeedPostsScreenState {
    data object Initial: FeedPostsScreenState()
    data object Loading: FeedPostsScreenState()
    data class Posts(
        val posts: List<FeedPost>,
        val isDownloading: Boolean = false
    ): FeedPostsScreenState()
}