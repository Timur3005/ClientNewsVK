package com.example.clientnewsvk.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.entity.StatisticItem
import com.example.clientnewsvk.getApplicationComponent

@Composable
fun FeedPostsScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit,
) {
    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())
    val vmState = viewModel.screenState.collectAsState(initial = FeedPostsScreenState.Initial)

    FeedPostsScreenContent(vmState, paddingValues, onCommentClickListener, viewModel)
}

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
private fun FeedPostsScreenContent(
    vmState: State<FeedPostsScreenState>,
    paddingValues: PaddingValues,
    onCommentClickListener: (StatisticItem, FeedPost) -> Unit,
    viewModel: NewsFeedViewModel,
) {
    when (val state = vmState.value) {

        FeedPostsScreenState.Initial -> {

        }

        is FeedPostsScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                posts = state.posts,
                onCommentClickListener = onCommentClickListener,
                onPostSwipedEndToStart = {
                    viewModel.remove(it)
                },
                onSharesClickListener = { statistic, post ->

                },
                onLikesClickListener = { _, post ->
                    viewModel.changeLikeStatus(post)
                },
                onViewsClickListener = { statistic, post ->

                },
                isNotDownloadingListener = {
                    viewModel.loadNextRecommendations()
                },
                isDownloading = state.isDownloading
            )
        }

        FeedPostsScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.DarkGray)
            }
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
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
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { post ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        onPostSwipedEndToStart(post)
                        true
                    } else {
                        false
                    }
                }
            )

            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) return@items

            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Delete")
                    }
                },
                content = {
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
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
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