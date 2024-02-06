package com.example.clientnewsvk.ui

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.clientnewsvk.R
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.domain.StatisticType

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
        Log.d("Recomposition", "Card")
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
        Image(
            painter = painterResource(feedPost.imageId),
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
            onSharesClickListener = onSharesClickListener
        )
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onViewsClickListener: (StatisticItem) -> Unit,
    onSharesClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikesClickListener: (StatisticItem) -> Unit
) {
    Log.d("Recomposition", "Statistics")
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
            IconWithText(R.drawable.ic_like, likesItem) {
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
    Log.d("Recomposition", "PostHeader")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Image(
            painter = painterResource(feedPost.iconId),
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
    onIconClick: () -> Unit,
) {
    Text(
        text = statistic.count.toString(),
        color = MaterialTheme.colorScheme.onSecondary
    )
    IconButton(onClick = { onIconClick() }) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            modifier = Modifier.padding(end = 7.dp),
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}