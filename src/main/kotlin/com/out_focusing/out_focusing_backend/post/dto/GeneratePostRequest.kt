package com.out_focusing.out_focusing_backend.post.dto

data class GeneratePostRequest(
    val albumId: Long,
    val isSecret: Boolean,
    val contents: LinkedHashSet<String>,
    val hashtags: LinkedHashSet<String>
)