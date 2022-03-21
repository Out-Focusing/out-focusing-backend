package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException
import com.out_focusing.out_focusing_backend.user.domain.Auth
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.out_focusing.out_focusing_backend.user.dto.UserRegisterRequest
import com.out_focusing.out_focusing_backend.user.repository.AuthRepository
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthApplication(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository
) {

    @Transactional
    fun registerUser(requestBody: UserRegisterRequest) {
        with(requestBody) {
            authRepository.findById(userId).ifPresent { throw CustomException(HttpStatus.CONFLICT, "유저 아이디가 중복되었습니다") }

            val createdUserProfile = UserProfile(
                userId = userId,
                name = name,
                contact = "",
                profileImage = "",
                readme = "",
                followedUsers = emptySet(),
                followingUsers = emptySet(),
                blockedUsers = emptySet(),
                albums = emptySet(),
                bookmarkAlbums = emptySet(),
                posts = emptySet(),
                bookmarkPost = emptySet()
            )

            userProfileRepository.save(createdUserProfile)

            val createdAuth = Auth(
                userId = userId,
                userProfile = createdUserProfile,
                password = password
            )

            authRepository.save(createdAuth)
        }
    }

}