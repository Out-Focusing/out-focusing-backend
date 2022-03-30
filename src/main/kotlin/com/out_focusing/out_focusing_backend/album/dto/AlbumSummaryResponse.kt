package com.out_focusing.out_focusing_backend.album.dto

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile

class AlbumSummaryResponse(
    val albumId: Long,
    val title: String,
    val thumbnail: String,
    val bookmark: Boolean,
    val bookmarksCount: Int,
    val secret: Boolean,
    val writerUserId: String
) {

    companion object {

        fun toAlbumSummaryResponse(album: Album, userProfile: UserProfile?): AlbumSummaryResponse = album.run {
            AlbumSummaryResponse(
                albumId,
                title,
                thumbnail,
                if (userProfile == null) false else bookmarkUsers.any { albumBookmark -> albumBookmark.userProfile == userProfile },
                bookmarkUsers.size,
                secret,
                writerUserProfile.userId
            )
        }

    }

}