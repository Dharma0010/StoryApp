package com.example.storyapp.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.ResultState
import com.example.storyapp.data.api.response.LoginResponse
import com.example.storyapp.data.api.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository): ViewModel() {

    fun register(name: String, email: String, password: String) = repository.register(name, email, password)

}