package com.eggbucket.eggbucket_android.model.login

data class LoginRequest(
    val phone: String,
    val pass: String
)

data class LoginResponse(
    val status: String,
    val token: String,
    val user: User
)

data class User(
    val _id: String
)
