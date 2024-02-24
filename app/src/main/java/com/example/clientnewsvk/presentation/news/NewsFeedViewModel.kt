package com.example.clientnewsvk.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.Mapper
import com.example.clientnewsvk.data.network.ApiFactory
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.presentation.main.HomeScreenState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {
    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        viewModelScope.launch {
            val accessToken = token?.accessToken ?: ""
            if (accessToken.isNotBlank()) {
                val posts = Mapper().mapWallContainerDtoToListFeedPost(
                    ApiFactory.apiService.responseRecommendedFeedPosts(token = accessToken)
                )
                Log.d("NewsFeedViewModel", "$posts")
                _screenState.value = HomeScreenState.Posts(posts)
            }
        }
    }

    private val _screenState = MutableLiveData<HomeScreenState>(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun updateStatisticList(
        statistic: StatisticItem,
        post: FeedPost,
    ) {
        val state = _screenState.value
        if (state !is HomeScreenState.Posts) return

        val oldPosts = state.posts
        val newPosts = oldPosts.toMutableList()
        newPosts.replaceAll {
            if (it == post) {
                it.copy(statistics = updateStatistic(statistic, it.statistics))
            } else {
                it
            }
        }
        _screenState.value = HomeScreenState.Posts(newPosts)
    }

    fun likePost(post: FeedPost, statistic: StatisticItem) {
        val exchangeList = (_screenState.value as HomeScreenState.Posts).posts.toMutableList()
        exchangeList.replaceAll {
            if (it.id == post.id) {
                it.copy(
                    isFavourite = !it.isFavourite,
                    statistics = updateStatistic(statistic, it.statistics)
                )
            } else {
                it
            }
        }
        _screenState.value = HomeScreenState.Posts(exchangeList)
    }

    private fun updateStatistic(
        statistic: StatisticItem,
        oldStatistics: List<StatisticItem>,
    ): List<StatisticItem> {
        val newStatistics = oldStatistics.toMutableList()
        newStatistics.replaceAll {
            if (it.type == statistic.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }
        return newStatistics
    }

    fun deleteItem(feedPost: FeedPost) {

        val state = _screenState.value
        if (state !is HomeScreenState.Posts) return

        val oldPosts = state.posts
        val newPosts = oldPosts.toMutableList()

        newPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(newPosts)
    }
}