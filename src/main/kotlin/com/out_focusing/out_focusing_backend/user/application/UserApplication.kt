package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.user.domain.FollowedUser
import com.out_focusing.out_focusing_backend.user.dto.UserProfileResponse
import com.out_focusing.out_focusing_backend.user.dto.UserProfileSummaryResponse
import com.out_focusing.out_focusing_backend.user.repository.FollowedUserRepository
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserApplication(
    private val userProfileRepository: UserProfileRepository,
    private val followedUserRepository: FollowedUserRepository
) {

    fun getUserProfile(userId: String): UserProfileResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        return userProfileRepository.findById(userId).map {
            UserProfileResponse.toUserProfileResponse(
                it,
                userProfile
            )
        }.orElseThrow { UserNotFoundException }
    }

    fun getMyProfile(): UserProfileResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        return UserProfileResponse.toUserProfileResponse(userProfile, userProfile)
    }

    fun getUsersFollowers(userId: String): List<UserProfileSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val myProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        return userProfileRepository.getUsersFollowers(userProfile).map {
            UserProfileSummaryResponse.toUserProfileSummaryResponse(it, myProfile)
        }
    }

    fun getUsersFollowings(userId: String): List<UserProfileSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val myProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        return userProfileRepository.getUsersFollowings(userProfile).map {
            UserProfileSummaryResponse.toUserProfileSummaryResponse(it, myProfile)
        }
    }

    @Transactional
    fun followUser(userId: String) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val myProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        if (myProfile == userProfile) {
            throw UserFollowYourselfException
        }

        if(followedUserRepository.existsByFollowingUserAndFollowedUser(myProfile, userProfile)) {
            throw UserAlreadyFollowedException
        }

        followedUserRepository.save(FollowedUser(myProfile, userProfile))
    }
}