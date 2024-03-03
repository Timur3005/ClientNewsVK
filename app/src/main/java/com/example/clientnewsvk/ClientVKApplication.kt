package com.example.clientnewsvk

import android.app.Application
import com.example.clientnewsvk.di.DaggerApplicationComponent

class ClientVKApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}