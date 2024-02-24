package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class AttachmentsDto(
    @SerializedName("photo") val photo: PhotoDto
)
