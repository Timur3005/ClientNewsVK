package com.example.clientnewsvk.domain

data class CommentItem(
    val id: Long,
    val postId: Long,
    val userName: String,
    val avatarUrl: String,
    val text: String,
    val time: String
)
