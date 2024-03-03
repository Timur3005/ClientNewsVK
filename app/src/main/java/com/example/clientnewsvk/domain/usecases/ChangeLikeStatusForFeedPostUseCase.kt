package com.example.clientnewsvk.domain.usecases

import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangeLikeStatusForFeedPostUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) = repository.changeLikeStatus(feedPost)
}