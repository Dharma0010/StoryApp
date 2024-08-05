package com.example.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.response.Story
import com.example.storyapp.data.api.response.UserModel
import com.example.storyapp.data.session.UserPreference
import kotlinx.coroutines.flow.Flow

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getStories(): LiveData<ResultState<List<Story>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getStories()
            val stories = response.listStory
            emit(ResultState.Success(stories))
        } catch (e: Exception) {
            Log.d("StoryRepository", "getStories: ${e.message.toString()} ")
            emit(ResultState.Error(e.message.toString()))
        }
    }
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository = StoryRepository(apiService, userPreference)
    }

}