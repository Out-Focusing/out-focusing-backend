package com.out_focusing.out_focusing_backend.user.dto

import com.out_focusing.out_focusing_backend.user.domain.UserProfile

data class UserProfileResponse(
    val userId: String,
    val name: String,
    val contact: String,
    val profileImage: String,
    val readme: String,
    val followers: Long,
    val followings: Long,
    val following: Boolean,
) {

    companion object {
        fun toUserProfileResponse(
            userProfile: UserProfile,
            myProfile: UserProfile?,
        ): UserProfileResponse {
            return UserProfileResponse(
                userProfile.userId,
                userProfile.name,
                userProfile.contact,
                userProfile.profileImage,
                userProfile.readme,
                userProfile.followedUsers.size.toLong(),
                userProfile.followingUsers.size.toLong(),
                if (myProfile == null) false else userProfile.followingUsers.any { it == myProfile }
            )
        }
    }

}