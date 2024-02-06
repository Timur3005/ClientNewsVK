package com.example.clientnewsvk.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientnewsvk.MainViewModel

@Composable
fun MainScreenBottomNavigation(viewModel: MainViewModel) {
    val listItem = listOf(NavigationItem.Main, NavigationItem.Favourite, NavigationItem.Profile)
    val feedPost = viewModel.feedPost.observeAsState()
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
    ) { paddingValues ->
        NewsCard(
            modifier = Modifier
                .padding(8.dp)
                .padding(paddingValues),
                feedPost = feedPost.value!!,
            onSharesClickListener = {
                viewModel.updateStatistic(it)
            },
            onCommentClickListener = {
                viewModel.updateStatistic(it)
            },
            onLikesClickListener = {
                viewModel.updateStatistic(it)
            },
            onViewsClickListener = {
                viewModel.updateStatistic(it)
            }
        )
    }
}