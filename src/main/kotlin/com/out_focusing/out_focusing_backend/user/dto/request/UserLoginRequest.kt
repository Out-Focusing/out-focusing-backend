package com.out_focusing.out_focusing_backend.user.dto.request

data class UserLoginRequest(
    val userId: String,
    val password: String
)
