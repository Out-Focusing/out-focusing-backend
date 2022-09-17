package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.QUserProfile
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserProfileCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserProfileCustomRepository {
    override fun getMyFollows(userProfile: UserProfile): List<UserProfile> {
        return jpaQueryFactory.selectFrom(QUserProfile.userProfile)
            .where(QUserProfile.userProfile.`in`(userProfile.followedUsers))
            .fetch()
    }
}