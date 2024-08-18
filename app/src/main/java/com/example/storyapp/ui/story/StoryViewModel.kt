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
import com.example.storyapp.data.api.response.FileUploadResponse
import com.example.storyapp.data.api.response.ListStory
import com.example.storyapp.data.api.response.UserModel
import kotlinx.coroutines.launch
import java.io.File

class StoryViewModel(private val repository: StoryRepository): ViewModel() {


    val stories: LiveData<PagingData<ListStory>> = repository.getStories().cachedIn(viewModelScope)

    private val _addStory = MutableLiveData<ResultState<FileUploadResponse>>()
    val addStory: LiveData<ResultState<FileUploadResponse>> = _addStory

    fun getLocationStories() = repository.getLocationStories()

    fun getDetailStory(id: String) = repository.getDetailStory(id = id)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun uploadStoryWithLocation(file: File, description: String, lat: Float? = null, lon: Float? = null) =
//        repository.addStoryWithLocation(imageFile = file, description = description, lat = lat, lon = lon)

    fun uploadStory(file: File, description: String, lat: Float? = null, lon: Float? = null) =
        viewModelScope.launch {
            _addStory.postValue(repository.addStory(imageFile = file, description = description, lat = lat, lon = lon))
        }
}