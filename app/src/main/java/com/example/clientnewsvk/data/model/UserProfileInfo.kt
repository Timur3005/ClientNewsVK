package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileInfo(
    @SerializedName("bdate") val birthday: String,
    @SerializedName("city") val city: City,
    @SerializedName("country") val country: Country,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("home_town") val homeTown: String,
    @SerializedName("id") val id: Long,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_200") val photoUrl: String,
    @SerializedName("screen_name") val screenName: String,
    @SerializedName("sex") val sex: Int
)