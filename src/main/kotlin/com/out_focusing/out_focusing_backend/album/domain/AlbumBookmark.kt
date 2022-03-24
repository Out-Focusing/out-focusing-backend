package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import javax.persistence.*

@Entity
@Table(name = "album_bookmark")
class AlbumBookmark(
    @EmbeddedId
    val albumBookmarkId: AlbumBookmarkId
) {

    @Embeddable
    data class AlbumBookmarkId(
        @JoinColumn(name = "user_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val userProfile: UserProfile,
        @JoinColumn(name = "album_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val album: Album
    ): java.io.Serializable

}