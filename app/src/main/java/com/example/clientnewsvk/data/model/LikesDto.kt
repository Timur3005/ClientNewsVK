package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("likes") val count: Long
)
