package com.example.clientnewsvk.domain

sealed interface AuthState {
    data object Initial: AuthState
    data object Authorized: AuthState
    data object NotAuthorized: AuthState
}