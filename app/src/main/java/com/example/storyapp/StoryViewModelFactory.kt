package com.example.storyapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.di.Injection
import com.example.storyapp.ui.story.StoryViewModel

class StoryViewModelFactory(private val repository: StoryRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            StoryViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
    companion object {
        fun getInstance(context: Context): StoryViewModelFactory =
            StoryViewModelFactory(Injection.provideStoryRepository(context))

    }
}