package com.example.clientnewsvk.domain.usecases

import com.example.clientnewsvk.domain.entity.ProfileItem
import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: NewsFeedRepository,
) {
    operator fun invoke(): StateFlow<ProfileItem> = repository.profileItem
}