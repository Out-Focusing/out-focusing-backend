package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "album_bookmark")
class AlbumBookmark(
    @EmbeddedId
    val albumBookmarkId: AlbumBookmarkId
) {

    @Embeddable
    data class AlbumBookmarkId(
        @JoinColumn(name = "user_id")
        @ManyToOne
        val userProfile: UserProfile,
        @JoinColumn(name = "album_id")
        @ManyToOne
        val album: Album
    ): java.io.Serializable

}