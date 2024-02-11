package com.example.clientnewsvk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientnewsvk.domain.CommentItem
import com.example.clientnewsvk.domain.FeedPost
import com.example.clientnewsvk.domain.HomeScreenState
import com.example.clientnewsvk.domain.StatisticItem
import com.example.clientnewsvk.ui.NavigationItem

class MainViewModel : ViewModel() {

    private val initComments = mutableListOf<CommentItem>().apply {
        repeat(10){
            add(
                CommentItem(id = it)
            )
        }
    }

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

    private var previousState: HomeScreenState? = initState

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

    fun showComments(feedPost: FeedPost, comments: List<CommentItem> = initComments){
        previousState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }

    fun closeComments(){
        _screenState.value = previousState ?: initState
    }
}