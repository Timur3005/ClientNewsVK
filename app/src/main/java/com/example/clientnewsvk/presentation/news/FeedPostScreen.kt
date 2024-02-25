package com.example.clientnewsvk.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem

@Composable
fun FeedPostScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel()
    val vmState = viewModel.screenState.observeAsState(FeedPostsScreenState.Initial)

    when (val state = vmState.value) {

        FeedPostsScreenState.Initial -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = Color.DarkGray)
            }
        }

        is FeedPostsScreenState.Posts -> {
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
                onLikesClickListener = { _, post ->
                    viewModel.exchangeLikedStatus(post)
                },
                onViewsClickListener = { statistic, post ->
                    viewModel.updateStatisticList(statistic, post)
                },
                isNotDownloadingListener = {
                    viewModel.loadRecommendations()
                },
                isDownloading = state.isDownloading
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
    isNotDownloadingListener: () -> Unit,
    isDownloading: Boolean,
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
        item {
            if (isDownloading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Color.DarkGray)
                }

            } else {
                SideEffect {
                    isNotDownloadingListener()
                }
            }
        }
    }
}