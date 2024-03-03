package com.example.clientnewsvk.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    val count: Long
) : Parcelable

enum class StatisticType{
    VIEWS, SHARES, COMMENTS, LIKES
}