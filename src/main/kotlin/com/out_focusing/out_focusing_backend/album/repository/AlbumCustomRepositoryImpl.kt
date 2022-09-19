package com.out_focusing.out_focusing_backend.album.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.QAlbum
import com.out_focusing.out_focusing_backend.album.domain.QAlbumBookmark
import com.out_focusing.out_focusing_backend.post.domain.QPost
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
class AlbumCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
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

    override fun getMyAlbum(userProfile: UserProfile, pageable: Pageable): List<Album> {
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
            .distinct()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    override fun getUserAlbum(userProfile: UserProfile, pageable: Pageable): List<Album> {
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
            .where(QAlbum.album.writerUserProfile.eq(userProfile).and(QAlbum.album.secret.isFalse))
            .distinct()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    override fun getAlbumDetail(albumId: Long, userProfile: UserProfile?): Album? {
        jpaQueryFactory.selectFrom(QAlbumBookmark.albumBookmark)
            .leftJoin(QAlbumBookmark.albumBookmark.album)
            .fetchJoin()
            .leftJoin(QAlbumBookmark.albumBookmark.userProfile)
            .fetchJoin()
            .where(QAlbumBookmark.albumBookmark.album.albumId.eq(albumId))
            .fetch()

        val permission = QAlbum.album.writerUserProfile.eq(userProfile).or(QAlbum.album.secret.isFalse)

        return jpaQueryFactory.selectFrom(QAlbum.album)
            .leftJoin(QAlbum.album.bookmarkUsers)
            .fetchJoin()
            .where(QAlbum.album.albumId.eq(albumId).and(permission))
            .fetchOne()
    }

    override fun findAlbumsByAlbumId(albumIds: List<Long>, userProfile: UserProfile?): List<Album> {
        jpaQueryFactory.selectFrom(QAlbumBookmark.albumBookmark)
            .leftJoin(QAlbumBookmark.albumBookmark.album)
            .fetchJoin()
            .leftJoin(QAlbumBookmark.albumBookmark.userProfile)
            .fetchJoin()
            .where(QAlbumBookmark.albumBookmark.album.albumId.`in`(albumIds))
            .fetch()

        return jpaQueryFactory.selectFrom(QAlbum.album)
            .leftJoin(QAlbum.album.bookmarkUsers)
            .fetchJoin()
            .where(QAlbum.album.albumId.`in`(albumIds))
            .distinct()
            .fetch()
    }

    override fun getUpdatedBookmarkAlbumAfterDate(
        userProfile: UserProfile?,
        date: LocalDateTime,
    ): List<Album> {
//        val bookmarkedAlbum = jpaQueryFactory.selectFrom(QAlbumBookmark.albumBookmark)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .where(QPost.post.createdAt.after(date)
                .and(QPost.post.album.bookmarkUsers.any().userProfile.eq(userProfile)))
            .orderBy(QPost.post.createdAt.desc())
            .fetch()
            .map { it.album }
            .distinct()

        jpaQueryFactory.selectFrom(QAlbum.album)
            .leftJoin(QAlbum.album.bookmarkUsers)
            .fetchJoin()
            .where(QAlbum.album.`in`(resultAlbums))
            .fetch()

        return resultAlbums
    }
}