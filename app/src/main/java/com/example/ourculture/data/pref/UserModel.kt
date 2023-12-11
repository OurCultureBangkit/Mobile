package com.example.ourculture.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val username: String,
    val avatar: String,
    val isLogin: Boolean = false
)
