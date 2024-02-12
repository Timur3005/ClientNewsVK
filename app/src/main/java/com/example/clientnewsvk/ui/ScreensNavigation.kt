package com.example.clientnewsvk.ui

sealed class ScreensNavigation(
    val route: String
) {
    data object Main: ScreensNavigation("main")
    data object NewsFeed: ScreensNavigation("news_feed")
    data object Comments: ScreensNavigation("comments")
    data object Favourite: ScreensNavigation("favourite")
    data object Profile: ScreensNavigation("profile")
}