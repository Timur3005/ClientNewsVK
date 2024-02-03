package com.example.clientnewsvk.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientnewsvk.R

@Composable
fun NewsCard() {
    ClientNewsVKTheme {
        Card(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ){
            PostHeader()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp)
            ) {
                Text(
                    text = "кабаныч, когда узнал, " +
                            "что если сотрудникам не платить они начинают умирать от голода"
                )
            }
            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp),
                contentScale = ContentScale.FillWidth
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(7.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    IconWithText(R.drawable.ic_views_count, "200")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.weight(1f)
                ) {
                    IconWithText(iconId = R.drawable.ic_share, text = "23")
                    IconWithText(iconId = R.drawable.ic_comment, text = "232")
                    IconWithText(iconId = R.drawable.ic_like, text = "53")
                }
            }
        }
    }
}

@Composable
private fun PostHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.post_comunity_thumbnail),
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
            Text(text = "уволено", color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "14:00", color = MaterialTheme.colorScheme.onSecondary)
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
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSecondary
    )
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = "",
        modifier = Modifier.padding(end = 7.dp),
        tint = MaterialTheme.colorScheme.onSecondary
    )
}

@Preview
@Composable
fun Dark(){
    ClientNewsVKTheme(darkTheme = true) {
        NewsCard()
    }
}

@Preview
@Composable
fun Light(){
    ClientNewsVKTheme(darkTheme = false) {
        NewsCard()
    }
}