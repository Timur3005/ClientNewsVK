package com.example.clientnewsvk.domain

import com.example.clientnewsvk.R

data class CommentItem(
    val id: Int,
    val userName: String = "User Name",
    val avatarId: Int = R.drawable.instagram_logo_2016,
    val text: String = "Text",
    val time: String = "14:00"
)
