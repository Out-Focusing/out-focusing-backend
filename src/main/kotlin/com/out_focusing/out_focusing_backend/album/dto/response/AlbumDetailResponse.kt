package com.out_focusing.out_focusing_backend.album.dto.response

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class AlbumDetailResponse(
    val albumId: Long,
    val title: String,
    val thumbnail: String,
    val bookmark: Boolean,
    val bookmarksCount: Int,
    val content: String,
    val posts: List<Any>,
    val secret: Boolean,
    val writerUserId: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val createdDate: LocalDateTime
) {

    companion object {
        fun toAlbumDetailResponse(album: Album, userProfile: UserProfile?): AlbumDetailResponse = album.run {
            AlbumDetailResponse(
                albumId,
                title,
                thumbnail,
                if(userProfile == null) false else bookmarkUsers.any { it.userProfile == userProfile },
                bookmarkUsers.size,
                content,
                listOf(),
                secret,
                writerUserProfile.userId,
                createdAt
            )
        }
    }

}