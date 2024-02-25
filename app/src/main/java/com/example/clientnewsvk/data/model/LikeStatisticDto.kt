package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class LikeStatisticDto(
    @SerializedName("count") val count: Long,
    @SerializedName("user_likes") val userLikes: Long
)
