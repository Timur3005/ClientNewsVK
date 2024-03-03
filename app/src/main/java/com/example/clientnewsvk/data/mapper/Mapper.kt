package com.example.clientnewsvk.data.mapper

import com.example.clientnewsvk.data.model.CommentsResponseContainerDto
import com.example.clientnewsvk.data.model.WallResponseContainerDto
import com.example.clientnewsvk.domain.entity.CommentItem
import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.entity.StatisticItem
import com.example.clientnewsvk.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

class Mapper @Inject constructor() {
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
                        isLiked = post.likeStatistic.userLikes > 0,
                        ownerId = post.ownerId
                    )
                )
            }
        }
    }

    fun mapCommentsResponseToCommentItems(response: CommentsResponseContainerDto): List<CommentItem>{
        val comments = response.response.comments
        val profiles = response.response.profiles

        return mutableListOf<CommentItem>().apply {
            for (comment in comments){
                val profile = profiles.find { it.id == comment.profileId } ?: continue
                add(
                    CommentItem(
                        id = comment.id,
                        postId = comment.postId,
                        userName = "${profile.firstName} ${profile.lastName}",
                        avatarUrl = profile.photoUrl,
                        text = comment.text,
                        time = mapLongTimeMilesToDDMMMMYYYY(comment.timeInMillis * 1000)
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