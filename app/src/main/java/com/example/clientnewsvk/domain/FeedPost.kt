package com.example.clientnewsvk.domain

import com.example.clientnewsvk.R

data class FeedPost(
    val id: Int,
    val groupName: String = "уволено",
    val time: String = "14:00",
    val iconId: Int = R.drawable.post_comunity_thumbnail,
    val text: String = "когда",
    val imageId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 123),
        StatisticItem(StatisticType.SHARES, 32),
        StatisticItem(StatisticType.COMMENTS, 45),
        StatisticItem(StatisticType.LIKES, 21)
    )
)
