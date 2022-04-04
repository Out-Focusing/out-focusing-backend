package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile

interface AlbumCustomRepository {

    fun removeAlbum(album: Album)
    fun getMyAlbum(userProfile: UserProfile): List<Album>
    fun getUserAlbum(userProfile: UserProfile): List<Album>
    fun getAlbumDetail(albumId: Long, userProfile: UserProfile?): Album?
}