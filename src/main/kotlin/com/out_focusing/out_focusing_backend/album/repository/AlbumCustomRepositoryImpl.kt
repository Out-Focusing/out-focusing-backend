package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.QAlbum
import com.out_focusing.out_focusing_backend.album.domain.QAlbumBookmark
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AlbumCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AlbumCustomRepository {

    @Transactional
    override fun removeAlbum(album: Album) {
        val qAlbumBookmark = QAlbumBookmark.albumBookmark
        val qAlbum = QAlbum.album

        jpaQueryFactory.delete(qAlbumBookmark)
            .where(qAlbumBookmark.album.eq(album))
            .execute()

        jpaQueryFactory.update(qAlbum)
            .set(qAlbum.deleted, true)
            .where(qAlbum.eq(album))
            .execute()
    }

    override fun getMyAlbum(userProfile: UserProfile): List<Album> {
        jpaQueryFactory.selectFrom(QAlbumBookmark.albumBookmark)
            .leftJoin(QAlbumBookmark.albumBookmark.album)
            .fetchJoin()
            .leftJoin(QAlbumBookmark.albumBookmark.userProfile)
            .fetchJoin()
            .where(QAlbumBookmark.albumBookmark.album.writerUserProfile.eq(userProfile))
            .fetch()

        return jpaQueryFactory.selectFrom(QAlbum.album)
            .leftJoin(QAlbum.album.bookmarkUsers)
            .fetchJoin()
            .where(QAlbum.album.writerUserProfile.eq(userProfile))
            .fetch()
    }

    override fun getUserAlbum(userProfile: UserProfile): List<Album> {
        jpaQueryFactory.selectFrom(QAlbumBookmark.albumBookmark)
            .leftJoin(QAlbumBookmark.albumBookmark.album)
            .fetchJoin()
            .leftJoin(QAlbumBookmark.albumBookmark.userProfile)
            .fetchJoin()
            .where(QAlbumBookmark.albumBookmark.album.writerUserProfile.eq(userProfile))

        return jpaQueryFactory.selectFrom(QAlbum.album)
            .leftJoin(QAlbum.album.bookmarkUsers)
            .fetchJoin()
            .where(QAlbum.album.writerUserProfile.eq(userProfile).and(QAlbum.album.secret.isFalse))
            .distinct()
            .fetch()
    }
}