package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.post.domain.*
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PostCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : PostCustomRepository {

    override fun findPostByPostId(postId: Long, userProfile: UserProfile?): Post {

        val permission = QPost.post.writerUserProfile.eq(userProfile).or(QPost.post.secret.isFalse)

        val post = jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.postId.eq(postId).and(permission))
            .fetchOne() ?: throw PostNotFoundException

        fetchJoinPosts(listOf(post))

        return post
    }

    override fun findPostsByPostIds(postIds: List<Long>, userProfile: UserProfile?): List<Post> {
        val permission = QPost.post.writerUserProfile.eq(userProfile).or(QPost.post.secret.isFalse)

        val result = jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.postId.`in`(postIds).and(permission))
            .fetch()

        fetchJoinPosts(result)

        return result;
    }

    override fun findPostsByAlbum(album: Album, pageable: Pageable, userProfile: UserProfile?): List<Post> {
        val permission = QPost.post.writerUserProfile.eq(userProfile).or(QPost.post.secret.isFalse)

        val result = jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.album.eq(album).and(permission))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        fetchJoinPosts(result)

        return result
    }

    override fun findPostsByUserProfile(
        targetUserProfile: UserProfile,
        pageable: Pageable,
        myUserProfile: UserProfile?,
    ): List<Post> {
        val permission = QPost.post.writerUserProfile.eq(myUserProfile).or(QPost.post.secret.isFalse)

        val result = jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.writerUserProfile.eq(targetUserProfile).and(permission))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        fetchJoinPosts(result)

        return result
    }

    override fun findPostsByUserBookmark(
        targetUserProfile: UserProfile,
        pageable: Pageable,
        myUserProfile: UserProfile?,
    ): List<Post> {
        val permission = QPostBookmark.postBookmark.post.writerUserProfile.eq(myUserProfile)
            .or(QPostBookmark.postBookmark.post.secret.isFalse)

        val subQuery = jpaQueryFactory.select(QPostBookmark.postBookmark.post)
            .from(QPostBookmark.postBookmark)
            .leftJoin(QPostBookmark.postBookmark.userProfile)
            .where(QPostBookmark.postBookmark.userProfile.eq(targetUserProfile).and(permission))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        fetchJoinPosts(subQuery)

        return subQuery
    }

    override fun findPostsByUsersAfterDate(
        userProfiles: List<UserProfile>,
        pageable: Pageable,
        myUserProfile: UserProfile?,
        date: LocalDateTime,
    ): List<Post> {
        val permission = QPost.post.writerUserProfile.eq(myUserProfile).or(QPost.post.secret.isFalse)

        val result = jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.writerUserProfile.`in`(userProfiles)
                .and(QPost.post.createdAt.after(date))
                .and(permission))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        fetchJoinPosts(result)

        return result
    }

    override fun searchPostsByKeyword(keyword: String, pageable: Pageable, userProfile: UserProfile?): List<Post> {
        val permission = QPost.post.writerUserProfile.eq(userProfile).or(QPost.post.secret.isFalse)

        val hashTagMatchTemplate = Expressions.numberTemplate(
            Double::class.javaObjectType,
            "function('match one index', {0}, {1})",
            QPostHashtag.postHashtag.hashtag,
            keyword
        )

        val result = jpaQueryFactory.selectFrom(QPost.post)
            .join(QPostHashtag.postHashtag)
            .on(QPostHashtag.postHashtag.post.eq(QPost.post))
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(hashTagMatchTemplate.gt(0).and(permission))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .distinct()
            .fetch()

        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.album)
            .fetchJoin()
            .leftJoin(QPost.post.writerUserProfile)
            .fetchJoin()
            .where(QPost.post.`in`(result))
            .fetch()

        fetchJoinPosts(result)

        return result
    }

    override fun fetchJoinPosts(posts: List<Post>) {
        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.postViews)
            .fetchJoin()
            .where(QPost.post.`in`(posts))
            .fetch()

        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.comments)
            .fetchJoin()
            .where(QPost.post.`in`(posts))
            .fetch()

        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.contents)
            .fetchJoin()
            .where(QPost.post.`in`(posts))
            .fetch()

        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.bookmarkUsers)
            .fetchJoin()
            .where(QPost.post.`in`(posts))
            .fetch()

        jpaQueryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.hashtags)
            .fetchJoin()
            .where(QPost.post.`in`(posts))
            .fetch()
    }

    override fun deleteAllPostHashtagByPost(post: Post) {
        jpaQueryFactory.delete(QPostHashtag.postHashtag)
            .where(QPostHashtag.postHashtag.post.eq(post))
            .execute()
    }

    override fun deleteAllPostContentsByPost(post: Post) {
        jpaQueryFactory.delete(QPostContent.postContent)
            .where(QPostContent.postContent.post.eq(post))
            .execute()
    }

    override fun deletePost(post: Post) {
        jpaQueryFactory.update(QPostContent.postContent)
            .set(QPostContent.postContent.deleted, true)
            .where(QPostContent.postContent.post.eq(post))
            .execute()

        jpaQueryFactory.update(QPostHashtag.postHashtag)
            .set(QPostHashtag.postHashtag.deleted, true)
            .where(QPostHashtag.postHashtag.post.eq(post))
            .execute()

        jpaQueryFactory.update(QPostViews.postViews)
            .set(QPostViews.postViews.deleted, true)
            .where(QPostViews.postViews.post.eq(post))
            .execute()

        jpaQueryFactory.update(QPostComment.postComment)
            .set(QPostComment.postComment.deleted, true)
            .where(QPostComment.postComment.post.eq(post))
            .execute()

        jpaQueryFactory.delete(QPostBookmark.postBookmark)
            .where(QPostBookmark.postBookmark.post.eq(post))
            .execute()

        jpaQueryFactory.update(QPost.post)
            .set(QPost.post.deleted, true)
            .where(QPost.post.eq(post))
            .execute()

    }


}