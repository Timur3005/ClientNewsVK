package com.example.clientnewsvk.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.clientnewsvk.R

sealed class NavigationItem(
    val nameId: Int,
    val imageVector: ImageVector,
) {
    data object Main : NavigationItem(
        R.string.main_screen,
        Icons.Outlined.Home
    )

    data object Favourite : NavigationItem(
        R.string.favourite,
        Icons.Outlined.Favorite
    )

    data object Profile : NavigationItem(
        R.string.main_screen,
        Icons.Outlined.Person
    )
}