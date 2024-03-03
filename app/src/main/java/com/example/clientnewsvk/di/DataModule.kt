package com.example.clientnewsvk.di

import android.app.Application
import com.example.clientnewsvk.data.network.ApiFactory
import com.example.clientnewsvk.data.repository.NewsFeedRepositoryImpl
import com.example.clientnewsvk.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {
        @Provides
        fun provideApiService() = ApiFactory.apiService

        @Provides
        fun provideVKStorage(application: Application) = VKPreferencesKeyValueStorage(application)
    }
}