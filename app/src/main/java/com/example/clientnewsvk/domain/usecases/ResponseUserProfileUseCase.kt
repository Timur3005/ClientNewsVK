package com.example.clientnewsvk.domain.usecases

import com.example.clientnewsvk.domain.repository.NewsFeedRepository

class ResponseUserProfileUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() = repository.responseProfileItem()
}