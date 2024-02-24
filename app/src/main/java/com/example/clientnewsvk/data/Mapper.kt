package com.example.clientnewsvk.data

import com.example.clientnewsvk.data.model.WallResponseContainerDto
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class Mapper {
    fun mapWallContainerDtoToListFeedPost(container: WallResponseContainerDto): List<FeedPost> {
        val postsDto = container.wallResponseDto.feedPosts
        val groupsDto = container.wallResponseDto.groups
        return mutableListOf<FeedPost>().apply {
            for (post in postsDto) {

                try {
                    if (post.attachments?.last()?.photo?.photoUrls.isNullOrEmpty()) continue
                } catch (e: NoSuchElementException) {
                    continue
                }

                val group = groupsDto.find { abs(it.id) == abs(post.sourceId) }
                add(
                    FeedPost(
                        id = post.id,
                        groupName = group?.name ?: "",
                        time = mapLongTimeMilesToDDMMMMYYYY(post.date * 1000),
                        iconUrl = group?.photoUrl ?: "",
                        text = post.bodyText,
                        imageUrl = post.attachments?.last()?.photo?.photoUrls?.last()?.url,
                        statistics = listOf(
                            StatisticItem(StatisticType.VIEWS, post.viewStatistic.count),
                            StatisticItem(StatisticType.SHARES, post.repostStatistic.count),
                            StatisticItem(StatisticType.COMMENTS, post.commentStatistic.count),
                            StatisticItem(StatisticType.LIKES, post.likeStatistic.count)
                        ),
                        isFavourite = post.isFavourite
                    )
                )
            }
        }
    }

    private fun mapLongTimeMilesToDDMMMMYYYY(timeMiles: Long): String {
        val date = Date(timeMiles)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}