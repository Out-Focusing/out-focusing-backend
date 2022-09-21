package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.UserProfile

interface UserProfileCustomRepository {
    fun getUsersFollowers(userProfile: UserProfile): List<UserProfile>
    fun getUsersFollowings(userProfile: UserProfile): List<UserProfile>
}