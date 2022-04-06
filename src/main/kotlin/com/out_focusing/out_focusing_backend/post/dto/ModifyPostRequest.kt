package com.out_focusing.out_focusing_backend.post.dto

data class ModifyPostRequest(
    val albumId: Long,
    val contents: LinkedHashSet<String>,
    val hashtags: LinkedHashSet<String>,
    val isSecret: Boolean
)