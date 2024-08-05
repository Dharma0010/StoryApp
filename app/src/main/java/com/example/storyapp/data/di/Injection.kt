package com.example.storyapp.data.di

import android.content.Context
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.api.ApiConfig
import com.example.storyapp.data.session.UserPreference
import com.example.storyapp.data.session.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideStoryRepository(context: Context): StoryRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preferences.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)

        return StoryRepository.getInstance(apiService = apiService, userPreference = preferences)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preferences.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(apiService = apiService, userPreferences = preferences)
    }
}