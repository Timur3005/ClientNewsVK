package com.example.clientnewsvk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.ui.NavigationItem

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(
                FeedPost(
                    id = it
                )
            )
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    private val _screenSelected = MutableLiveData<NavigationItem>(NavigationItem.Main)
    val screenSelected: LiveData<NavigationItem> = _screenSelected

    fun updateSelectedScreen(screen: NavigationItem){
        _screenSelected.value = screen
    }

    fun updateStatisticList(
        statistic: StatisticItem,
        post: FeedPost,
    ) {
        val oldPosts = _feedPosts.value ?: throw IllegalStateException()
        val newPosts = oldPosts.toMutableList()
        newPosts.replaceAll {
            if (it == post) {
                it.copy(statistics = updateStatistic(statistic, it.statistics))
            } else {
                it
            }
        }
        _feedPosts.value = newPosts
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
        val oldPosts = _feedPosts.value ?: throw IllegalStateException()
        val newPosts = oldPosts.toMutableList()
        newPosts.remove(feedPost)
        _feedPosts.value = newPosts
    }
}