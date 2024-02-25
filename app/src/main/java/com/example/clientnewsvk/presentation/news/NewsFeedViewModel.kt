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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val newsFeedRepository = NewsFeedRepository(application)

    init {
        responseRecommendations()
    }

    fun loadRecommendations() {
        _screenState.value = FeedPostsScreenState.Posts(
            posts = newsFeedRepository.feedPosts,
            isDownloading = true
        )
        responseRecommendations()
    }

    private fun responseRecommendations() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsFeedRepository.loadRecommendations()
            }
            _screenState.value = FeedPostsScreenState.Posts(
                posts = newsFeedRepository.feedPosts
            )
        }
    }

    private val _screenState = MutableLiveData<FeedPostsScreenState>(FeedPostsScreenState.Initial)
    val screenState: LiveData<FeedPostsScreenState> = _screenState

    fun updateStatisticList(
        statistic: StatisticItem,
        post: FeedPost,
    ) {
        val state = _screenState.value
        if (state !is FeedPostsScreenState.Posts) return

        val oldPosts = state.posts
        val newPosts = oldPosts.toMutableList()
        newPosts.replaceAll {
            if (it == post) {
                it.copy(statistics = updateStatistic(statistic, it.statistics))
            } else {
                it
            }
        }
        _screenState.value = FeedPostsScreenState.Posts(newPosts)
    }

    fun exchangeLikedStatus(post: FeedPost) {
        viewModelScope.launch {
            val newLikesCount = withContext(Dispatchers.IO) {
                if (!post.isLiked) {
                    newsFeedRepository.addLike(post)
                } else {
                    newsFeedRepository.deleteLike(post)
                }
            }
            val exchangeList = (_screenState.value as FeedPostsScreenState.Posts).posts.toMutableList()
            val newStat = post.statistics.toMutableList().apply {
                removeIf { it.type == StatisticType.LIKES }
                add(
                    StatisticItem(StatisticType.LIKES, newLikesCount)
                )
            }
            val newPost = post.copy(statistics = newStat, isLiked = !post.isLiked)
            exchangeList[exchangeList.indexOf(post)] = newPost
            _screenState.value = FeedPostsScreenState.Posts(exchangeList)
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
        if (state !is FeedPostsScreenState.Posts) return

        val oldPosts = state.posts
        val newPosts = oldPosts.toMutableList()

        newPosts.remove(feedPost)
        _screenState.value = FeedPostsScreenState.Posts(newPosts)
    }
}