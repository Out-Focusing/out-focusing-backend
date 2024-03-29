package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.user.domain.FollowedUser
import com.out_focusing.out_focusing_backend.user.dto.request.ModifyUserProfileRequest
import com.out_focusing.out_focusing_backend.user.dto.response.UserProfileResponse
import com.out_focusing.out_focusing_backend.user.dto.response.UserProfileSummaryResponse
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

    fun searchUsers(keyword: String): List<UserProfileSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        return userProfileRepository.searchUsersByKeyword(keyword, userProfile).map {
            UserProfileSummaryResponse.toUserProfileSummaryResponse(it, userProfile)
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

        if (followedUserRepository.existsByFollowingUserAndFollowedUser(myProfile, userProfile)) {
            throw AlreadyUserFollowedException
        }

        followedUserRepository.save(FollowedUser(myProfile, userProfile))
    }

    @Transactional
    fun unfollowUser(userId: String) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val myProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        if (myProfile == userProfile)
            throw UserUnfollowYourselfException

        if (followedUserRepository.existsByFollowingUserAndFollowedUser(myProfile, userProfile)) {
            followedUserRepository.deleteByFollowingUserAndFollowedUser(myProfile, userProfile)
        } else {
            throw UserNotFollowedException
        }
    }

    @Transactional
    fun modifyUserProfile(request: ModifyUserProfileRequest) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val myProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        request.apply {
            name?.let { myProfile.name = it }
            contact?.let { myProfile.contact = it }
            profileImage?.let { myProfile.profileImage = it }
            readme?.let { myProfile.readme = it }
        }

        userProfileRepository.save(myProfile)
    }
}