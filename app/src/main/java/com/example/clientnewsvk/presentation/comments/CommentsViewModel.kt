package com.example.clientnewsvk.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientnewsvk.data.repository.NewsFeedRepository
import com.example.clientnewsvk.domain.FeedPost
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application,
): ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        getComments(feedPost)
    }

    private fun getComments(feedPost: FeedPost) {
        viewModelScope.launch {
            _screenState.value =
                CommentsScreenState.Comments(feedPost, repository.getComments(feedPost))
        }
    }
}