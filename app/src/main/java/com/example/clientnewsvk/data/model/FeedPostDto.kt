package com.example.clientnewsvk.data.model

import com.google.gson.annotations.SerializedName

data class FeedPostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("source_id") val sourceId: Long,
    @SerializedName("comments") val commentStatistic: CommentStatisticDto,
    @SerializedName("is_favorite") val isFavourite: Boolean,
    @SerializedName("likes") val likeStatistic: LikeStatisticDto,
    @SerializedName("text") val bodyText: String,
    @SerializedName("reposts") val repostStatistic: RepostStatisticDto,
    @SerializedName("views") val viewStatistic: ViewStatisticDto,
    @SerializedName("attachments") val attachments: List<AttachmentsDto>?
)
