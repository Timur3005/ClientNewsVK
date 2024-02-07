package com.example.clientnewsvk.ui

sealed class ScreensNavigation(
    val route: String
) {
    data object Main: ScreensNavigation("main")
    data object Favourite: ScreensNavigation("favourite")
    data object Profile: ScreensNavigation("profile")
}