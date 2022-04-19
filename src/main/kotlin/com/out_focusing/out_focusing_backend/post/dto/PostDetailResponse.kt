package com.out_focusing.out_focusing_backend.post.dto

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class PostDetailResponse(
    val postId: Long,
    val title: String,
    val contents: List<String>,
    val albumId: Long,
    val bookmark: Boolean,
    val bookmarksCount: Int,
    val secret: Boolean,
    val writerUserId: String,
    val views: Long,
    val hashtags: List<String>,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val createDate: LocalDateTime
) {

    companion object {
        fun toPostDetailResponse(post: Post, userProfile: UserProfile?): PostDetailResponse {
            post.run {
                return PostDetailResponse(
                    postId = postId,
                    title = album.title,
                    contents = contents.map { it.fileUrl },
                    albumId = album.albumId,
                    bookmark = if(userProfile== null) false else bookmarkUsers.any { it.userProfile == userProfile},
                    bookmarksCount = bookmarkUsers.size,
                    secret = secret,
                    writerUserId = writerUserProfile.userId,
                    hashtags = hashtags.map { it.hashtag },
                    views = postViews.size.toLong(),
                    createDate = createdAt
                )
            }
        }
    }

}