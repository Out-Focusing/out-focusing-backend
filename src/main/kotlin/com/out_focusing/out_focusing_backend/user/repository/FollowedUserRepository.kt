package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.FollowedUser
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowedUserRepository : CrudRepository<FollowedUser, Long> {
    fun existsByFollowingUserAndFollowedUser(followingUser: UserProfile, followedUser: UserProfile): Boolean
    fun deleteByFollowingUserAndFollowedUser(followingUser: UserProfile, followedUser: UserProfile)
}