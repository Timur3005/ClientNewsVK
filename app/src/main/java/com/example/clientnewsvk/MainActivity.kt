package com.example.clientnewsvk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.clientnewsvk.ui.MainScreenBottomNavigation
import com.example.clientnewsvk.ui.theme.ClientNewsVKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            ClientNewsVKTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MainScreenBottomNavigation(
                        viewModel = viewModel,
                        onViewsClickListener = { statisticItem, feedPost ->
                            viewModel.updateStatisticList(statisticItem, feedPost)
                        },
                        onSharesClickListener = { statisticItem, feedPost ->
                            viewModel.updateStatisticList(statisticItem, feedPost)
                        },
                        onCommentClickListener = { statisticItem, feedPost ->
                            viewModel.updateStatisticList(statisticItem, feedPost)
                        },
                        onLikesClickListener = { statisticItem, feedPost ->
                            viewModel.updateStatisticList(statisticItem, feedPost)
                        },
                        onPostSwipedEndToStart = {
                            viewModel.deleteItem(it)
                        }
                    )
                }
            }
        }
    }
}