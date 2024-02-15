package com.example.clientnewsvk.domain

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.clientnewsvk.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable{
    companion object{
        val NavigationType = object : NavType<FeedPost>(false){
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key, FeedPost::class.java)
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
