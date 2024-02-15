package com.example.clientnewsvk.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    val count: Int
) : Parcelable

enum class StatisticType{
    VIEWS, SHARES, COMMENTS, LIKES
}