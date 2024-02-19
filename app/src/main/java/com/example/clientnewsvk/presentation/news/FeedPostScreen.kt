package com.example.clientnewsvk.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.presentation.main.HomeScreenState
import com.example.clientnewsvk.domain.StatisticItem

@Composable
fun FeedPostScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel()
    val vmState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val state = vmState.value){
        HomeScreenState.Initial -> {

        }
        is HomeScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                posts = state.posts,
                onCommentClickListener = onCommentClickListener,
                onPostSwipedEndToStart = {
                    viewModel.deleteItem(it)
                },
                onSharesClickListener = { statistic, post ->
                    viewModel.updateStatisticList(statistic, post)
                },
                onLikesClickListener = {statistic, post ->
                    viewModel.updateStatisticList(statistic, post)
                },
                onViewsClickListener = {statistic, post ->
                    viewModel.updateStatisticList(statistic, post)
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun FeedPosts(
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onPostSwipedEndToStart: (FeedPost) -> Unit,
    onSharesClickListener: (StatisticItem, FeedPost) -> Unit,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit,
    onLikesClickListener: (StatisticItem, FeedPost) -> Unit,
    onViewsClickListener: (StatisticItem, FeedPost) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 72.dp,
            start = 8.dp,
            end = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { post ->

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