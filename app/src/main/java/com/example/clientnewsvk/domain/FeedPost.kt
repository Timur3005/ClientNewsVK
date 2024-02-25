package com.example.clientnewsvk.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val id: Long,
    val groupName: String,
    val time: String,
    val iconUrl: String,
    val text: String,
    val imageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean,
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
