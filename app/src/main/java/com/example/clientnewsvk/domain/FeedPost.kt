package com.example.clientnewsvk.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Long,
    val groupName: String = "уволено",
    val time: String = "14:00",
    val iconUrl: String,
    val text: String = "когда",
    val imageUrl: String?,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 123),
        StatisticItem(StatisticType.SHARES, 32),
        StatisticItem(StatisticType.COMMENTS, 45),
        StatisticItem(StatisticType.LIKES, 21)
    ),
    val userLikes: Long,
    val ownerId: Long
): Parcelable{
    companion object{
        val NavigationType = object : NavType<FeedPost>(false){
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }

        }
    }
}
