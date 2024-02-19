package com.example.clientnewsvk.presentation.main

import com.example.clientnewsvk.domain.FeedPost

sealed interface HomeScreenState {
    data object Initial: HomeScreenState
    data class Posts(val posts: List<FeedPost>): HomeScreenState
}