package com.eggbucket.eggbucket_android.model.login

data class LoginRequest(
    val phone: String,
    val pass: String
)

data class LoginResponse(
    val success: Boolean,
    val userId: String?,  // Assuming the response contains a userId
    val message: String
)
