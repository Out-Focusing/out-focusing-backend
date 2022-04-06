package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.post.domain.*
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

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

        jpaQueryFactory.selectFrom(QPostContent.postContent)
            .leftJoin(QPostContent.postContent)
            .fetchJoin()
            .where(QPostContent.postContent.post.postId.eq(postId))
            .fetch()

        jpaQueryFactory.selectFrom(QPostHashtag.postHashtag)
            .leftJoin(QPostHashtag.postHashtag)
            .fetchJoin()
            .where(QPostHashtag.postHashtag.post.postId.eq(postId))
            .fetch()

        jpaQueryFactory.selectFrom(QPostViews.postViews)
            .leftJoin(QPostViews.postViews.post)
            .fetchJoin()
            .leftJoin(QPostViews.postViews.readerUserProfile)
            .fetchJoin()
            .where(QPostViews.postViews.post.postId.eq(postId))
            .fetch()

        jpaQueryFactory.selectFrom(QPostComment.postComment)
            .leftJoin(QPostComment.postComment)
            .fetchJoin()
            .where(QPostComment.postComment.post.postId.eq(postId))
            .fetch()

        jpaQueryFactory.selectFrom(QPostBookmark.postBookmark)
            .leftJoin(QPostBookmark.postBookmark.post)
            .fetchJoin()
            .where(QPostBookmark.postBookmark.post.postId.eq(postId))
            .fetch()

        return post
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

        jpaQueryFactory.update(QPostBookmark.postBookmark)
            .set(QPostBookmark.postBookmark.deleted, true)
            .where(QPostBookmark.postBookmark.post.eq(post))
            .execute()

        jpaQueryFactory.update(QPost.post)
            .set(QPost.post.deleted, true)
            .where(QPost.post.eq(post))
            .execute()

    }


}