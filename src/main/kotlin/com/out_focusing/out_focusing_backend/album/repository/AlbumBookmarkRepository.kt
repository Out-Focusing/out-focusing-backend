package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.AlbumBookmark
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumBookmarkRepository : CrudRepository<AlbumBookmark, AlbumBookmark.AlbumBookmarkId> {

}