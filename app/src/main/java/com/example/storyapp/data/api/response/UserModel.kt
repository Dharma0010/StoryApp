package com.example.storyapp.data.api.response

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)