package com.example.clientnewsvk.navigation

import android.net.Uri
import com.example.clientnewsvk.domain.FeedPost
import com.google.gson.Gson

sealed class ScreensNavigation(
    val route: String,
) {
    data object Main : ScreensNavigation(MAIN_ROUTE)
    data object NewsFeed : ScreensNavigation(NEWS_FEED_ROUTE)
    data object Comments : ScreensNavigation(COMMENTS_ROUTE) {
        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostJson = Gson().toJson(feedPost)
            return "$COMMENTS_ROOT/${Uri.encode(feedPostJson)}"
        }
    }
    data object Favourite : ScreensNavigation(FAVOURITE_ROUTE)
    data object Profile : ScreensNavigation(PROFILE_ROUTE)

    companion object {
        const val KEY_FEED_POST = "feed_post"
        private const val COMMENTS_ROOT = "comments"
        private const val FAVOURITE_ROUTE = "favourite"
        private const val PROFILE_ROUTE = "profile"
        private const val NEWS_FEED_ROUTE = "news_feed"
        private const val MAIN_ROUTE = "main"
        private const val COMMENTS_ROUTE = "$COMMENTS_ROOT/{$KEY_FEED_POST}"
    }
}