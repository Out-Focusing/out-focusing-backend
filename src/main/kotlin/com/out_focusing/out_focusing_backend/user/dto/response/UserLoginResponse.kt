package com.out_focusing.out_focusing_backend.user.dto.response

data class UserLoginResponse(
    val token: String,
    val refreshToken: String
)