package com.example.clientnewsvk.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientnewsvk.MainViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    Scaffold(
        bottomBar = {
            NavigationBar {
                Log.d("Recomposition", "NavigationBar")
                val state = remember {
                    mutableIntStateOf(0)
                }
                listItem.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = state.intValue == index,
                        onClick = { state.intValue = index },
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
    ) {
        val vmState = viewModel.feedPosts.observeAsState(listOf())
        LazyColumn {
            items(items = vmState.value, key = { it.id }) { post ->

                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    onPostSwipedEndToStart(post)
                }

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {},
                    dismissContent = {
                        NewsCard(
                            modifier = Modifier.padding(8.dp),
                            feedPost = post,
                            onSharesClickListener = {
                                onSharesClickListener(it, post)
                            },
                            onCommentClickListener = {
                                onCommentClickListener(it, post)
                            },
                            onLikesClickListener = {
                                onLikesClickListener(it, post)
                            },
                            onViewsClickListener = {
                                onViewsClickListener(it, post)
                            }
                        )
                    }
                )
            }
        }
    }
}