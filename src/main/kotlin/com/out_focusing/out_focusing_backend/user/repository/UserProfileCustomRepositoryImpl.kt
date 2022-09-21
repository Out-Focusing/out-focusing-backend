package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.QUserProfile
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserProfileCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserProfileCustomRepository {
    override fun getUsersFollowers(userProfile: UserProfile): List<UserProfile> {
        return jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .distinct()
            .leftJoin(QUserProfile.userProfile.followingUsers)
            .fetchJoin()
            .where(QUserProfile.userProfile.followingUsers.contains(userProfile))
            .fetch()
    }
}