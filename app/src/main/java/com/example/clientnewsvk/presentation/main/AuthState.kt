package com.example.clientnewsvk.presentation.main

sealed interface AuthState {
    data object Initial: AuthState
    data object Authorized: AuthState
    data object NotAuthorized: AuthState
}