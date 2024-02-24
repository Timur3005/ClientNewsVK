package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class WallResponseContainerDto(
    @SerializedName("response") val wallResponseDto: WallResponseDto
)
