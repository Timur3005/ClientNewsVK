package com.example.clientnewsvk.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.clientnewsvk.domain.entity.ProfileItem

@Composable
fun ProfileScreen(
) {

}

@Composable
private fun ProfileCard(
    profile: ProfileItem
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = profile.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Column {
                Text(text = profile.firstName)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = profile.surname)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = profile.birthday)
            }
        }
    }
}