package com.example.clientnewsvk.di

import android.app.Application
import com.example.clientnewsvk.presentation.main.MainActivity
import com.example.clientnewsvk.presentation.viewmodelfactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun getCommentsComponentFactory(): CommentsComponent.Factory
    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}