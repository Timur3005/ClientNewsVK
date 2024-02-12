package com.example.clientnewsvk.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainScreenNavigationGraph(
    newsFeedScreen: @Composable () -> Unit,
    commentsScreen: @Composable () -> Unit
) {
    navigation(
        startDestination = ScreensNavigation.NewsFeed.route,
        route = ScreensNavigation.Main.route
    ) {
        composable(ScreensNavigation.NewsFeed.route) {
            newsFeedScreen()
        }
        composable(ScreensNavigation.Comments.route) {
            commentsScreen()
        }
    }
}