package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)