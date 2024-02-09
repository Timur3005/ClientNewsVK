package com.example.clientnewsvk.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.clientnewsvk.MainViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenBottomNavigation(
    viewModel: MainViewModel,
    onViewsClickListener: (StatisticItem, FeedPost) -> Unit,
    onSharesClickListener: (StatisticItem, FeedPost) -> Unit,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit,
    onLikesClickListener: (StatisticItem, FeedPost) -> Unit,
    onPostSwipedEndToStart: (FeedPost) -> Unit
) {
    val listItem = listOf(NavigationItem.Main, NavigationItem.Favourite, NavigationItem.Profile)
    val navState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                listItem.forEach { navigationItem ->
                    NavigationBarItem(
                        selected =
                        navState.navController.currentBackStackEntryAsState()
                            .value?.destination?.route == navigationItem.screen.route
                        ,
                        onClick = {
                            navState.navigateTo(navigationItem.screen.route)
                        },
                        icon = {
                            Icon(navigationItem.imageVector, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = navigationItem.nameId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavigationGraph(
            navController = navState.navController,
            mainScreen = {
                HomeScreen(
                    viewModel,
                    paddingValues,
                    onPostSwipedEndToStart,
                    onSharesClickListener,
                    onCommentClickListener,
                    onLikesClickListener,
                    onViewsClickListener
                )
            },
            favouriteScreen = { TextCounter(item = NavigationItem.Favourite, paddingValues) },
            profileScreen = { TextCounter(item = NavigationItem.Profile, paddingValues) }
        )
    }
}

@Composable
private fun TextCounter(item: NavigationItem, paddingValues: PaddingValues) {
    var countClick by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        text = "$item $countClick",
        modifier = Modifier
            .padding(paddingValues)
            .clickable { countClick++ }
    )
}