package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.*
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PostCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : PostCustomRepository {

    override fun findPostByPostId(postId: Long, userProfile: UserProfile?): Post? {

        val permission = QPost.post.writerUserProfile.eq(userProfile).or(QPost.post.secret.isFalse)

        val post = jpaQueryFactory.selectFrom(QPost.post)
            .where(QPost.post.postId.eq(postId).and(permission))
            .fetchOne()

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

}