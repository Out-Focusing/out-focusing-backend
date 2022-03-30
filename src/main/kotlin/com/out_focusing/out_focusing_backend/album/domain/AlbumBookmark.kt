package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import javax.persistence.*

@Entity
@Table(name = "album_bookmark", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "album_id"])])
class AlbumBookmark(
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val userProfile: UserProfile,

    @JoinColumn(name = "album_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val album: Album
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val albumBookmarkId: Long = 0
}