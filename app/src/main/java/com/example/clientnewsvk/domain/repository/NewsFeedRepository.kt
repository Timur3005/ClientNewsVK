package com.example.clientnewsvk.domain.repository

import com.example.clientnewsvk.presentation.main.AuthState
import com.example.clientnewsvk.domain.entity.CommentItem
import com.example.clientnewsvk.domain.entity.FeedPost
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    val recommendations: StateFlow<List<FeedPost>>
    suspend fun loadNextRecommendationData()
    suspend fun deletePost(feedPost: FeedPost)
    suspend fun responseCommentsByFeedPost(feedPost: FeedPost)

    val commentsFlow: SharedFlow<List<CommentItem>>
    suspend fun changeLikeStatus(feedPost: FeedPost)

    val authFlow: StateFlow<AuthState>
    suspend fun responseAuthStateFlow()

}