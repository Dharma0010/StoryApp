package com.example.storyapp.ui.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.data.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email = email, password = password)
//    fun saveSession(user: UserModel) {
//        viewModelScope.launch {
//            repository.saveSession(user)
//        }
//    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}