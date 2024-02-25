package com.example.clientnewsvk.data.repository

import android.app.Application
import com.example.clientnewsvk.data.Mapper
import com.example.clientnewsvk.data.network.ApiFactory
import com.example.clientnewsvk.domain.FeedPost
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.delay

class NewsFeedRepository(
    private val application: Application,
) {

    private val apiService = ApiFactory.apiService
    private val mapper = Mapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts

    private var nextFrom: String? = null

    suspend fun loadRecommendations() {
        if (nextFrom == null && _feedPosts.isNotEmpty()) return
        val startFrom = nextFrom
        val result = if (startFrom == null) {
            apiService.responseRecommendedFeedPosts(token = getToken())
        }else{
            apiService.responseRecommendedFeedPosts(token = getToken(), startFrom = startFrom)
        }
        nextFrom = result.wallResponseDto.nextFrom
        _feedPosts.addAll(mapper.mapWallContainerDtoToListFeedPost(result))
    }

    suspend fun addLike(feedPost: FeedPost): Long {
        val likes = apiService.addLike(
            token = getToken(),
            itemId = feedPost.id,
            ownerId = -feedPost.ownerId
        )
        return likes.response.count
    }

    suspend fun deleteLike(feedPost: FeedPost): Long {
        val likes = apiService.deleteLike(
            token = getToken(),
            itemId = feedPost.id,
            ownerId = -feedPost.ownerId
        )
        return likes.response.count
    }

    private fun getToken(): String {
        val storage = VKPreferencesKeyValueStorage(application)
        return VKAccessToken.restore(storage)?.accessToken
            ?: throw RuntimeException("token is invalid")
    }
}