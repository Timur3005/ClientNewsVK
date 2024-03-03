package com.example.clientnewsvk.di

import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.presentation.main.AuthViewModel
import com.example.clientnewsvk.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(NewsFeedViewModel::class)
    fun bindNewsFeedViewModel(impl: NewsFeedViewModel): ViewModel

}