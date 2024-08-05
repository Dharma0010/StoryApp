package com.example.storyapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.di.Injection
import com.example.storyapp.login.LoginViewModel
import com.example.storyapp.signup.RegisterViewModel
import com.example.storyapp.story.StoryViewModel

class ViewModelFactory (private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory = ViewModelFactory(Injection.provideAuthRepository(context))
    }
}