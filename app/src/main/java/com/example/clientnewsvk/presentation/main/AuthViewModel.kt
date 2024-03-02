package com.example.clientnewsvk.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.repository.NewsFeedRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)
    val authState = repository.authFlow.onStart { getAuthState() }

    fun getAuthState() {
        viewModelScope.launch {
            repository.responseAuthStateFlow()
        }
    }
}