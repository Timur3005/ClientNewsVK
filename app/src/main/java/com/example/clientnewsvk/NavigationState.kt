package com.example.clientnewsvk

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.ui.ScreensNavigation

class NavigationState(
    val navController: NavHostController,
) {
    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: FeedPost){
        navController.navigate(ScreensNavigation.Comments.getRouteWithArgs(feedPost))
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController(),
): NavigationState {
    return remember {
        NavigationState(navController)
    }
}