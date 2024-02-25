package com.example.clientnewsvk.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.repository.NewsFeedRepository
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.domain.StatisticType
import com.example.clientnewsvk.presentation.main.HomeScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val newsFeedRepository = NewsFeedRepository(application)

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsFeedRepository.loadRecommendation()
            }
            _screenState.value = HomeScreenState.Posts(newsFeedRepository.feedPosts)
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

    fun exchangeLikedStatus(post: FeedPost) {
        viewModelScope.launch {
            val newLikesCount = withContext(Dispatchers.IO) {
                if (post.userLikes == 0L) {
                    newsFeedRepository.addLike(post)
                } else {
                    newsFeedRepository.deleteLike(post)
                }
            }
            val exchangeList = (_screenState.value as HomeScreenState.Posts).posts.toMutableList()
            exchangeList.replaceAll { foundPost ->
                if (foundPost.id == post.id) {
                    foundPost.copy(
                        userLikes = if (foundPost.userLikes > 0) {
                            0
                        } else {
                            1
                        },
                        statistics = foundPost.statistics.toMutableList().apply {
                            replaceAll { statisticItem ->
                                if (statisticItem.type == StatisticType.LIKES) {
                                    statisticItem.copy(count = newLikesCount)
                                } else {
                                    statisticItem
                                }
                            }
                        }
                    )
                } else {
                    foundPost
                }
            }
            _screenState.value = HomeScreenState.Posts(exchangeList)
        }
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