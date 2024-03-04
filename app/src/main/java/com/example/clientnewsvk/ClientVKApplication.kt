package com.example.clientnewsvk

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.clientnewsvk.di.ApplicationComponent
import com.example.clientnewsvk.di.DaggerApplicationComponent

class ClientVKApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent{
    return (LocalContext.current.applicationContext as ClientVKApplication).component
}