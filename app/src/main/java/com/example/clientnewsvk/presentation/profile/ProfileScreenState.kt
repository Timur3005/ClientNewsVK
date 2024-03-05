package com.example.clientnewsvk.presentation.profile

import com.example.clientnewsvk.domain.entity.ProfileItem

sealed interface ProfileScreenState {
    data object Initial : ProfileScreenState
    data object Loading : ProfileScreenState
    data class Profile(
        val profile: ProfileItem,
    ) : ProfileScreenState
}