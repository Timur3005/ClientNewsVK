package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class ContainerIsLikedCheckDto(
    @SerializedName("response") val container: IsLikedContainer
)
