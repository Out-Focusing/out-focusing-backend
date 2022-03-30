package com.out_focusing.out_focusing_backend.album.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class AlbumDetailResponse(
    val albumId: Long,
    val title: String,
    val thumbnail: String,
    val bookmark: Boolean,
    val bookmarksCount: Long,
    val content: String,
    val posts: List<Any>,
    val secret: Boolean,
    val writerUserId: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val createdDate: LocalDateTime
)