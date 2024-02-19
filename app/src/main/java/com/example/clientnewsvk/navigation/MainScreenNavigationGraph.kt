package com.example.clientnewsvk.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.navigation.ScreensNavigation.Companion.KEY_FEED_POST

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.mainScreenNavigationGraph(
    newsFeedScreen: @Composable () -> Unit,
    commentsScreen: @Composable (FeedPost) -> Unit,
) {
    navigation(
        startDestination = ScreensNavigation.NewsFeed.route,
        route = ScreensNavigation.Main.route
    ) {
        composable(ScreensNavigation.NewsFeed.route) {
            newsFeedScreen()
        }
        composable(
            route = ScreensNavigation.Comments.route,
            arguments = listOf(
                navArgument(KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {
            val feedPost = it.arguments?.getParcelable(KEY_FEED_POST, FeedPost::class.java) ?: throw RuntimeException("")
            commentsScreen(feedPost)
        }
    }
}