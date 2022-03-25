package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumRepository: CrudRepository<Album, Long>, AlbumCustomRepository {
}