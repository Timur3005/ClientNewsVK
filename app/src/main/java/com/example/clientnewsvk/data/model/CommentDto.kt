package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val profileId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("post_id") val postId: Long,
    @SerializedName("date") val timeInMillis: Long
)
