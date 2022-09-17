package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.UserProfile

interface UserProfileCustomRepository {
    fun getMyFollows(userProfile: UserProfile): List<UserProfile>
}