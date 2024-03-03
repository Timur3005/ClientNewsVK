package com.example.clientnewsvk.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.domain.usecases.GetAuthStateUseCase
import com.example.clientnewsvk.domain.usecases.ResponseAuthStateUseCase
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val responseAuthStateUseCase: ResponseAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateUseCase().onStart { responseAuthState() }

    fun responseAuthState() {
        viewModelScope.launch {
            responseAuthStateUseCase()
        }
    }
}