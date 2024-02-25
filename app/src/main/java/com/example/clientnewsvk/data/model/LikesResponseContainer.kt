package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class LikesResponseContainer(
    @SerializedName("response") val response: LikesDto
)
