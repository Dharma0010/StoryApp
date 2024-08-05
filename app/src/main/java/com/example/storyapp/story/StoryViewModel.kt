package com.example.storyapp.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.api.response.UserModel

class StoryViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStories() = repository.getStories()


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}