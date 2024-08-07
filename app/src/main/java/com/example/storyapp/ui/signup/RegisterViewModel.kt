package com.example.storyapp.ui.signup

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.AuthRepository

class RegisterViewModel(private val repository: AuthRepository): ViewModel() {

    fun register(name: String, email: String, password: String) = repository.register(name, email, password)

}