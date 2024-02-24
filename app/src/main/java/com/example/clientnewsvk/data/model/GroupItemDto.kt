package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class GroupItemDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("photo_200") val photoUrl: String
)
