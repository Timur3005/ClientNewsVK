package com.example.clientnewsvk.ui

import androidx.activity.compose.BackHandler
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
import com.example.clientnewsvk.MainViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.HomeScreenState
import com.example.clientnewsvk.domain.StatisticItem

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    onPostSwipedEndToStart: (FeedPost) -> Unit,
    onSharesClickListener: (StatisticItem, FeedPost) -> Unit,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit,
    onLikesClickListener: (StatisticItem, FeedPost) -> Unit,
    onViewsClickListener: (StatisticItem, FeedPost) -> Unit
) {
    val vmState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val state = vmState.value){
        is HomeScreenState.Comments -> {
            CommentsScreen(
                post = state.feedPost,
                comments = state.comments,
                navigationClickListener = {
                    viewModel.closeComments()
                }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }
        HomeScreenState.Initial -> {}
        is HomeScreenState.Posts -> {
            FeedPosts(
                paddingValues,
                state.posts,
                onPostSwipedEndToStart,
                onSharesClickListener,
                onCommentClickListener,
                onLikesClickListener,
                onViewsClickListener
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