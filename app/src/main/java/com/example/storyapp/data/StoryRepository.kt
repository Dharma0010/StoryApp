package com.example.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.data.api.remote.ApiService
import com.example.storyapp.data.api.response.DetailStoryResponse
import com.example.storyapp.data.api.response.FileUploadResponse
import com.example.storyapp.data.api.response.ListStory
import com.example.storyapp.data.api.response.UserModel
import com.example.storyapp.data.session.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getStories(): LiveData<ResultState<List<ListStory>>> = liveData {
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

    fun getDetailStory(id: String): LiveData<ResultState<DetailStoryResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getDetailStories(id = id)
            emit(ResultState.Success(response))
        } catch (e :Exception) {
            Log.d("StoryRepository", "getDetailStories: ${e.message.toString()} ")
            emit(ResultState.Error(e.message.toString()))
        }
    }
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun addStory(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.addStory(file = multipartBody, description = requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }

    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository = StoryRepository(apiService, userPreference)
    }

}