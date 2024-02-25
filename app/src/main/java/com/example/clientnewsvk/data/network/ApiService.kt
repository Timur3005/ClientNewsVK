package com.example.clientnewsvk.data.network

import com.example.clientnewsvk.data.model.ContainerIsLikedCheckDto
import com.example.clientnewsvk.data.model.LikesResponseContainer
import com.example.clientnewsvk.data.model.WallResponseContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("$NAME_OF_GET_RECOMMENDED_FEED_POSTS_METHOD?")
    suspend fun responseRecommendedFeedPosts(
        @Query("v") version: String = API_VERSION,
        @Query("access_token") token: String
    ): WallResponseContainerDto

    @GET("$NAME_OF_ADD_LIKE?")
    suspend fun addLike(
        @Query("v") version: String = API_VERSION,
        @Query("access_token") token: String,
        @Query("type") type: String = "post",
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    ): LikesResponseContainer

    @GET("$NAME_OF_DELETE_LIKE?")
    suspend fun deleteLike(
        @Query("v") version: String = API_VERSION,
        @Query("access_token") token: String,
        @Query("type") type: String = "post",
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    ): LikesResponseContainer

    @GET("$NAME_OF_LIKE_CHECK?")
    suspend fun isLiked(
        @Query("v") version: String = API_VERSION,
        @Query("type") type: String = "post",
        @Query("user_id") userId: Long,
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    ): ContainerIsLikedCheckDto

    companion object{
        private const val API_VERSION = "5.199"
        private const val NAME_OF_GET_RECOMMENDED_FEED_POSTS_METHOD = "newsfeed.getRecommended"
        private const val NAME_OF_ADD_LIKE = "likes.add"
        private const val NAME_OF_DELETE_LIKE = "likes.delete"
        private const val NAME_OF_LIKE_CHECK = "likes.isLiked"
    }
}