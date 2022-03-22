package com.out_focusing.out_focusing_backend.user.dto

data class ReissueTokenRequest(
    val grantType: String,
    val refreshToken: String
)
