package com.example.clientnewsvk.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.clientnewsvk.R

sealed class BottomNavigationItem(
    val screen: ScreensNavigation,
    val nameId: Int,
    val imageVector: ImageVector,
) {
    data object Main : BottomNavigationItem(
        ScreensNavigation.Main,
        R.string.main_screen,
        Icons.Outlined.Home
    )

    data object Favourite : BottomNavigationItem(
        ScreensNavigation.Favourite,
        R.string.favourite,
        Icons.Outlined.Favorite
    )

    data object Profile : BottomNavigationItem(
        ScreensNavigation.Profile,
        R.string.profile,
        Icons.Outlined.Person
    )
}