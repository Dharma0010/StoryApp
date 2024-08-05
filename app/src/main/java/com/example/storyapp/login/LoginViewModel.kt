package com.example.storyapp.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.api.response.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email = email, password = password)
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}