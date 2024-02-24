package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class WallResponseDto(
    @SerializedName("items") val feedPosts: List<FeedPostDto>,
    @SerializedName("groups") val groups: List<GroupItemDto>
)
