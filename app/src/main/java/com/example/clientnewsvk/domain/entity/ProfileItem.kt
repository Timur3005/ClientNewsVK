package com.example.clientnewsvk.domain.entity

data class ProfileItem(
    val id: Int,
    val homeTown: String,
    val status: String,
    val avatarUrl: String,
    val birthday: String,
    val firstName: String,
    val surname: String,
    val city: String,
    val screenName: String,
    val sex: Int
)