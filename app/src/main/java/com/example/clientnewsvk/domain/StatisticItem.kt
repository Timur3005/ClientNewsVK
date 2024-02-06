package com.example.clientnewsvk.domain

data class StatisticItem(
    val type: StatisticType,
    val count: Int
)
enum class StatisticType{
    VIEWS, SHARES, COMMENTS, LIKES
}