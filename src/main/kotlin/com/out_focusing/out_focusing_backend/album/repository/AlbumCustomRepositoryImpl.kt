package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.QAlbum
import com.out_focusing.out_focusing_backend.album.domain.QAlbumBookmark
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AlbumCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): AlbumCustomRepository {

    @Transactional
    override fun removeAlbum(album: Album) {
        val qAlbumBookmark = QAlbumBookmark.albumBookmark
        val qAlbum = QAlbum.album
        jpaQueryFactory.delete(qAlbumBookmark)
            .where(qAlbumBookmark.albumBookmarkId.album.eq(album))
            .execute()

        jpaQueryFactory.delete(qAlbum)
            .where(qAlbum.eq(album))
            .execute()
    }

}