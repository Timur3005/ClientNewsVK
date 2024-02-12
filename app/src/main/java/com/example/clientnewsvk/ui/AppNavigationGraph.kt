package com.example.clientnewsvk.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    newsFeedScreen: @Composable () -> Unit,
    commentsScreen: @Composable () -> Unit,
    favouriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.Main.route
    ){
        mainScreenNavigationGraph(newsFeedScreen, commentsScreen)
        composable(ScreensNavigation.Favourite.route){
            favouriteScreen()
        }
        composable(ScreensNavigation.Profile.route){
            profileScreen()
        }
    }
}