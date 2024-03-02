package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class CommentsContainerDto(
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>,
)
