package com.example.clientnewsvk.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.clientnewsvk.R
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.entity.StatisticItem
import com.example.clientnewsvk.domain.entity.StatisticType

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewsClickListener: (StatisticItem) -> Unit,
    onSharesClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        PostHeader(feedPost)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
        ) {
            Text(
                text = feedPost.text
            )
        }
        AsyncImage(
            model = feedPost.imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            contentScale = ContentScale.FillWidth
        )
        Statistics(
            statistics = feedPost.statistics,
            onViewsClickListener = onViewsClickListener,
            onLikesClickListener = onLikesClickListener,
            onCommentClickListener = onCommentClickListener,
            onSharesClickListener = onSharesClickListener,
            isLiked = feedPost.isLiked
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onViewsClickListener: (StatisticItem) -> Unit,
    onSharesClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit,
    isLiked: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.findItemByType(StatisticType.VIEWS)
            IconWithText(R.drawable.ic_views_count, viewsItem) {
                onViewsClickListener(viewsItem)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.weight(1f)
        ) {
            val shareItem = statistics.findItemByType(StatisticType.SHARES)
            IconWithText(R.drawable.ic_share, shareItem) {
                onSharesClickListener(shareItem)
            }
            val commentItem = statistics.findItemByType(StatisticType.COMMENTS)
            IconWithText(R.drawable.ic_comment, commentItem) {
                onCommentClickListener(commentItem)
            }
            val likesItem = statistics.findItemByType(StatisticType.LIKES)
            IconWithText(
                iconId = if (isLiked) {
                    R.drawable.ic_like_set
                } else {
                    R.drawable.ic_like
                },
                statistic = likesItem,
                iconTint = if (isLiked) {
                    Color.Red
                } else {
                    MaterialTheme.colorScheme.onSecondary
                }
            ) {
                onLikesClickListener(likesItem)
            }
        }
    }
}

private fun List<StatisticItem>.findItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        AsyncImage(
            model = feedPost.iconUrl,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        )
        Column(
            modifier = Modifier
                .padding(7.dp)
                .weight(1f)
        ) {
            Text(text = feedPost.groupName, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = feedPost.time, color = MaterialTheme.colorScheme.onSecondary)
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun IconWithText(
    iconId: Int,
    statistic: StatisticItem,
    iconTint: Color = MaterialTheme.colorScheme.onSecondary,
    onIconClick: () -> Unit,
) {
    Text(
        text = reformatStatistic(statistic.count),
        color = MaterialTheme.colorScheme.onSecondary
    )
    IconButton(
        modifier = Modifier.size(30.dp),
        onClick = { onIconClick() }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            modifier = Modifier.padding(end = 7.dp),
            tint = iconTint
        )
    }
}

private fun reformatStatistic(count: Long): String {
    return if (count > 100000) {
        String.format("%sK", (count / 1000))
    } else if (count in 1001..100000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}