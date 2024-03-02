package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseContainerDto(
    @SerializedName("response") val response: CommentsContainerDto
)
