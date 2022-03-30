package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.global.security.JwtUtil
import com.out_focusing.out_focusing_backend.user.domain.Auth
import com.out_focusing.out_focusing_backend.user.domain.AuthRefreshToken
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.out_focusing.out_focusing_backend.user.dto.*
import com.out_focusing.out_focusing_backend.user.repository.AuthRefreshTokenRepository
import com.out_focusing.out_focusing_backend.user.repository.AuthRepository
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthApplication(
    private val authRepository: AuthRepository,
    private val userProfileRepository: UserProfileRepository,
    private val authRefreshTokenRepository: AuthRefreshTokenRepository,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {

    private val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Transactional
    fun registerUser(requestBody: UserRegisterRequest) {
        with(requestBody) {
            userProfileRepository.findById(userId).ifPresent { throw UserIdConflictException }

            val createdUserProfile = UserProfile(
                userId = userId,
                name = name,
                contact = "",
                profileImage = "",
                readme = "",
                followedUsers = listOf(),
                followingUsers = listOf(),
                blockedUsers = listOf(),
                albums = listOf(),
                bookmarkAlbums = listOf(),
                posts = listOf(),
                bookmarkPost = listOf()
            )

            userProfileRepository.save(createdUserProfile)

            val createdAuth = Auth(
                userId = userId, userProfile = createdUserProfile, password = passwordEncoder.encode(password)
            )

            authRepository.save(createdAuth)
        }
    }

    fun loginUser(requestBody: UserLoginRequest): UserLoginResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    requestBody.userId, requestBody.password
                )
            )
        } catch (_: Exception) {
            throw FailedLoginException
        }

        val refreshToken = jwtUtil.generateRefreshToken(requestBody.userId)
        val accessToken = jwtUtil.generateAccessToken(requestBody.userId)

        authRefreshTokenRepository.save(AuthRefreshToken(refreshToken))
        return UserLoginResponse(accessToken, refreshToken)
    }

    fun reissueToken(requestBody: ReissueTokenRequest): ReissueTokenResponse {
        if (requestBody.grantType == "refresh_token") {
            if (!jwtUtil.isRefreshTokenExpired(requestBody.refreshToken)) {
                authRefreshTokenRepository.findByRefreshToken(requestBody.refreshToken)
                    .orElseThrow { WrongTokenException }

                val username = jwtUtil.extractUsername(requestBody.refreshToken, false)

                authRepository.findById(username).orElseThrow { UserNotExistsException }

                val accessToken = jwtUtil.generateAccessToken(username)
                val refreshToken = jwtUtil.generateRefreshToken(username)

                return ReissueTokenResponse(accessToken, refreshToken)
            } else {
                throw TokenTimeExpiredException
            }
        }
        throw WrongGrantTypeException
    }

}