package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.domain.Pageable

interface AlbumCustomRepository {

    fun removeAlbum(album: Album)
    fun getMyAlbum(userProfile: UserProfile, pageable: Pageable): List<Album>
    fun getUserAlbum(userProfile: UserProfile, pageable: Pageable): List<Album>
    fun getAlbumDetail(albumId: Long, userProfile: UserProfile?): Album?
    fun findAlbumsByAlbumId(albumIds: List<Long>, userProfile: UserProfile?): List<Album>
}