package com.example.clientnewsvk.domain.usecases

import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke() = repository.recommendations
}