package com.eggbucket.eggbucket_android.model.loginmodel

data class LoginRequest(
    val phone: String,
    val pass: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: Any? // Replace 'Any' with a specific data class if you know the structure
)