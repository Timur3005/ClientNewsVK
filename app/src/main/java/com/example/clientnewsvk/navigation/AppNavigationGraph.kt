package com.example.clientnewsvk.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.clientnewsvk.domain.FeedPost

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    newsFeedScreen: @Composable () -> Unit,
    commentsScreen: @Composable (FeedPost) -> Unit,
    favouriteScreen: @Composable () -> Unit,
    profileScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.Main.route
    ){
        mainScreenNavigationGraph(
            newsFeedScreen = newsFeedScreen,
            commentsScreen = {
                commentsScreen(it)
            }
        )
        composable(ScreensNavigation.Favourite.route){
            favouriteScreen()
        }
        composable(ScreensNavigation.Profile.route){
            profileScreen()
        }
    }
}