package com.example.clientnewsvk.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.presentation.main.HomeScreenState
import com.example.clientnewsvk.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it
                )
            )
        }
    }

    private val initState = HomeScreenState.Posts(initList)

    private val _screenState = MutableLiveData<HomeScreenState>(initState)
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