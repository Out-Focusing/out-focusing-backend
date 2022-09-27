package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.QFollowedUser
import com.out_focusing.out_focusing_backend.user.domain.QUserProfile
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserProfileCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserProfileCustomRepository {
    override fun getUsersFollowers(userProfile: UserProfile): List<UserProfile> {
        val result = jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .join(QFollowedUser.followedUser1)
            .on(QFollowedUser.followedUser1.followingUser.eq(QUserProfile.userProfile)
                .and(QFollowedUser.followedUser1.followedUser.eq(userProfile)))
            .fetch()

        jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .leftJoin(QUserProfile.userProfile.followedUsers)
            .fetchJoin()
            .where(QUserProfile.userProfile.`in`(result))
            .fetch()

        return result
    }

    override fun getUsersFollowings(userProfile: UserProfile): List<UserProfile> {
        val result = jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .join(QFollowedUser.followedUser1)
            .on(QFollowedUser.followedUser1.followedUser.eq(QUserProfile.userProfile)
                .and(QFollowedUser.followedUser1.followingUser.eq(userProfile)))
            .fetch()

        jpaQueryFactory.selectFrom(QFollowedUser.followedUser1)
            .leftJoin(QFollowedUser.followedUser1.followingUser)
            .fetchJoin()
            .where(QFollowedUser.followedUser1.followedUser.`in`(result))
            .fetch()

        jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .leftJoin(QUserProfile.userProfile.followedUsers)
            .fetchJoin()
            .where(QUserProfile.userProfile.`in`(result))
            .fetch()

        return result
    }

    override fun searchUsersByKeyword(keyword: String, userProfile: UserProfile?): List<UserProfile> {
        val result = jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .where(QUserProfile.userProfile.name.contains(keyword))
            .fetch()

        jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .leftJoin(QUserProfile.userProfile.followedUsers)
            .fetchJoin()
            .where(QUserProfile.userProfile.`in`(result))
            .fetch()

        return result
    }
}