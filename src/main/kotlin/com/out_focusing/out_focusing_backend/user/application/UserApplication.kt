package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException
import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.user.dto.UserProfileResponse
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserApplication(
    private val userProfileRepository: UserProfileRepository,
) {

    fun getUserProfile(userId: String): UserProfileResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails

        val userProfile = userProfileRepository.findById(userDetails.username).orElseThrow { UserNotExistsException }

        return userProfileRepository.findById(userId).map {
            UserProfileResponse.toUSerProfileResponse(
                it,
                userProfile
            )
        }.orElseThrow { UserNotFoundException }
    }

}