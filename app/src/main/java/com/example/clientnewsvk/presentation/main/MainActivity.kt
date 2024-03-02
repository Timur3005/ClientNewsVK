package com.example.clientnewsvk.presentation.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clientnewsvk.ui.theme.ClientNewsVKTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientNewsVKTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val viewModel: AuthViewModel = viewModel()
                    val screenState =
                        viewModel.authState.observeAsState(initial = AuthState.Initial)
                    val launcher = rememberLauncherForActivityResult(
                        contract = VK.getVKAuthActivityResultContract(),
                        onResult = {
                            viewModel.performedAuthState(it)
                        }
                    )
                    when (screenState.value) {
                        AuthState.Authorized -> {
                            MainScreen(application)
                        }

                        AuthState.Initial -> {
                            Box{}
                        }

                        AuthState.NotAuthorized -> {
                            AuthScreen {
                                launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                            }
                        }
                    }
                }
            }
        }
    }
}