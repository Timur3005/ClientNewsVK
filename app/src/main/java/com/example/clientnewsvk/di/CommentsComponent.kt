package com.example.clientnewsvk.di

import com.example.clientnewsvk.domain.entity.FeedPost
import com.example.clientnewsvk.presentation.viewmodelfactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsComponent {
    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsComponent
    }
}