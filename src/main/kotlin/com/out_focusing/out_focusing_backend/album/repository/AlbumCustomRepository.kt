package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album

interface AlbumCustomRepository {

    fun removeAlbum(album: Album);


}