package com.example.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.data.api.ApiService
import com.example.storyapp.data.api.response.LoginResponse
import com.example.storyapp.data.api.response.LoginResult
import com.example.storyapp.data.api.response.RegisterResponse
import com.example.storyapp.data.api.response.Story
import com.example.storyapp.data.api.response.UserModel
import com.example.storyapp.data.session.UserPreference
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }



    suspend fun logout() {
        userPreference.logout()
    }

    fun register(name: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name = name, email = email, password = password)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            Log.d("StoryRepository", "getStories: ${e.message.toString()} ")
            emit(ResultState.Error(e.message.toString()))
        }
    }
    fun login(email: String, password: String): LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email = email, password = password)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            Log.d("StoryRepository", "getStories: ${e.message.toString()} ")
            emit(ResultState.Error(e.message.toString()))
        }
    }



    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreference
        ): AuthRepository = AuthRepository(apiService, userPreferences)
    }
}