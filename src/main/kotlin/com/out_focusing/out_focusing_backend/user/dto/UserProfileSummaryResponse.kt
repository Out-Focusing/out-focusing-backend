package com.out_focusing.out_focusing_backend.user.dto

import com.out_focusing.out_focusing_backend.user.domain.UserProfile

data class UserProfileSummaryResponse(
    val userId: String,
    val name: String,
    val profileImage: String,
    val following: Boolean
) {

    companion object {
        fun toUserProfileSummaryResponse(
            userProfile: UserProfile,
            myProfile: UserProfile?
        ): UserProfileSummaryResponse {
            return UserProfileSummaryResponse(
                userId = userProfile.userId,
                name = userProfile.name,
                profileImage = userProfile.profileImage,
                following = if(myProfile == null) false else userProfile.followingUsers.contains(myProfile)
            )
        }
    }

}