package com.example.clientnewsvk.domain.usecases

import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ResponseAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    suspend operator fun invoke() = repository.responseAuthStateFlow()
}