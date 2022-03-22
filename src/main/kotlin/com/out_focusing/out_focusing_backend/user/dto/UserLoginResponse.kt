package com.out_focusing.out_focusing_backend.user.dto

data class UserLoginResponse(
    val token: String,
    val refreshToken: String
)