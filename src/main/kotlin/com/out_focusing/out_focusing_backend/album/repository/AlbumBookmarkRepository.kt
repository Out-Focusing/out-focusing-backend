package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.AlbumBookmark
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumBookmarkRepository : CrudRepository<AlbumBookmark, Long> {
    fun existsAlbumBookmarkByUserProfileAndAlbum(userProfile: UserProfile, album: Album): Boolean
    fun deleteAlbumBookmarkByUserProfileAndAlbum(userProfile: UserProfile, album: Album)
}