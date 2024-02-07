package com.example.clientnewsvk.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    mainScreen: @Composable () -> Unit,
    favouriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.Main.route
    ){
        composable(ScreensNavigation.Main.route){
            mainScreen()
        }
        composable(ScreensNavigation.Favourite.route){
            favouriteScreen()
        }
        composable(ScreensNavigation.Profile.route){
            profileScreen()
        }
    }
}