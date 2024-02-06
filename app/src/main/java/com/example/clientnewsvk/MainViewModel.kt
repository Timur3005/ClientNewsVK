package com.example.clientnewsvk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun updateStatistic(
        statistic: StatisticItem,
    ) {
        val newStatistics =
            _feedPost.value?.statistics?.toMutableList() ?: throw IllegalStateException()
        newStatistics.replaceAll {
            if (it.type == statistic.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }
        _feedPost.value = _feedPost.value?.copy(
            statistics = newStatistics
        )
    }
}