package com.out_focusing.out_focusing_backend.post.dto.response

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile

class PostSummaryResponse(
    val postId: Long,
    val title: String,
    val content: List<String>,
    val bookmark: Boolean,
    val bookmarksCount: Int,
    val secret: Boolean,
    val writerUserId: String,
) {

    companion object {
        fun toPostSummaryResponse(post: Post, userProfile: UserProfile?): PostSummaryResponse = post.run {
            PostSummaryResponse(
                postId,
                album.title,
                contents.map { it.fileUrl },
                if(userProfile == null) false else bookmarkUsers.any { postBookmark -> postBookmark.userProfile == userProfile },
                bookmarkUsers.size,
                secret,
                writerUserProfile.userId
            )
        }
    }

}