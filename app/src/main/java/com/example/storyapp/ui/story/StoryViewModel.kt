package com.example.storyapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.ResultState
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.api.response.ListStory
import com.example.storyapp.data.api.response.UserModel
import kotlinx.coroutines.launch
import java.io.File

class StoryViewModel(private val repository: StoryRepository): ViewModel() {

    private val _stories = MutableLiveData<ResultState<List<ListStory>>>()
    val stories: LiveData<PagingData<ListStory>> = repository.getStories().cachedIn(viewModelScope)

//    fun getStories() {
//        viewModelScope.launch {
//            _stories.postValue(ResultState.Loading)
//            _stories.postValue(repository.getStories())
//        }
//    }

    fun getDetailStory(id: String) = repository.getDetailStory(id = id)


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun addStory(file: File, description: String) = repository.addStory(file, description)

}