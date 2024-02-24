package com.example.clientnewsvk.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.clientnewsvk.navigation.rememberNavigationState
import com.example.clientnewsvk.navigation.AppNavigationGraph
import com.example.clientnewsvk.navigation.BottomNavigationItem
import com.example.clientnewsvk.presentation.comments.CommentsScreen
import com.example.clientnewsvk.presentation.news.FeedPostScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen() {
    val listItem = listOf(
        BottomNavigationItem.Main,
        BottomNavigationItem.Favourite,
        BottomNavigationItem.Profile
    )
    val navState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                listItem.forEach { navigationItem ->
                    val selected = navState
                        .navController
                        .currentBackStackEntryAsState()
                        .value?.destination?.hierarchy
                        ?.any {
                            it.route == navigationItem.screen.route
                        } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navState.navigateTo(navigationItem.screen.route)
                            }
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
            newsFeedScreen = {
                FeedPostScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = { _, feedPost ->
                        navState.navigateToComments(feedPost)
                    }
                )
            },
            commentsScreen = { feedPost ->
                CommentsScreen(
                    navigationClickListener = {
                        navState.navController.popBackStack()
                    },
                    feedPost = feedPost
                )
            },
            favouriteScreen = {
                TextCounter(item = BottomNavigationItem.Favourite, paddingValues)
            },
            profileScreen = {
                TextCounter(item = BottomNavigationItem.Profile, paddingValues)
            }
        )
    }
}

@Composable
private fun TextCounter(item: BottomNavigationItem, paddingValues: PaddingValues) {
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