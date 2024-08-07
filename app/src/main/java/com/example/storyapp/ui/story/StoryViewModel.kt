package com.example.storyapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.api.response.UserModel
import java.io.File

class StoryViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStories() = repository.getStories()

    fun getDetailStory(id: String) = repository.getDetailStory(id = id)


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun addStory(file: File, description: String) = repository.addStory(file, description)

}