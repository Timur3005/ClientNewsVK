package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class ContainerProfileInfoDto(
    @SerializedName("response") val userProfileInfo: UserProfileInfo
)