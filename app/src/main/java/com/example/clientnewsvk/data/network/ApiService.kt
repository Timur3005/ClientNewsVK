package com.example.clientnewsvk.data.network

import com.example.clientnewsvk.data.model.WallResponseContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("$NAME_OF_GET_RECOMMENDED_FEED_POSTS_METHOD?")
    suspend fun responseRecommendedFeedPosts(
        @Query("v") version: String = API_VERSION,
        @Query("access_token") token: String
    ): WallResponseContainerDto

    companion object{
        private const val API_VERSION = "5.199"
        private const val NAME_OF_GET_RECOMMENDED_FEED_POSTS_METHOD = "newsfeed.getRecommended"
    }
}