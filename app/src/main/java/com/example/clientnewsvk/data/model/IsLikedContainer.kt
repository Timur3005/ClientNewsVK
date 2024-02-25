package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class IsLikedContainer(
    @SerializedName("liked") val isLiked: Int
)
