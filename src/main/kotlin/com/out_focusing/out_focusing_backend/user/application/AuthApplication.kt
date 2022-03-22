package com.out_focusing.out_focusing_backend.user.application

import com.out_focusing.out_focusing_backend.global.error.CustomException
import com.out_focusing.out_focusing_backend.global.security.JwtUtil
import com.out_focusing.out_focusing_backend.user.domain.Auth
import com.out_focusing.out_focusing_backend.user.domain.AuthRefreshToken
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import com.out_focusing.out_focusing_backend.user.dto.*
import com.out_focusing.out_focusing_backend.user.repository.AuthRefreshTokenRepository
import com.out_focusing.out_focusing_backend.user.repository.AuthRepository
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.http.HttpStatus
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
            userProfileRepository.findById(userId)
                .ifPresent { throw CustomException(HttpStatus.CONFLICT, "유저 아이디가 중복되었습니다.") }

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
                password = passwordEncoder.encode(password)
            )

            authRepository.save(createdAuth)
        }
    }

    fun loginUser(requestBody: UserLoginRequest): UserLoginResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    requestBody.userId,
                    requestBody.password
                )
            )
        } catch (_: Exception) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다.")
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
                    .orElseThrow { throw CustomException(HttpStatus.UNAUTHORIZED, "변조된 토큰입니다.") }

                val username = jwtUtil.extractUsername(requestBody.refreshToken, false)

                authRepository.findById(username)
                    .orElseThrow { CustomException(HttpStatus.UNAUTHORIZED, "로그인 정보가 존재하지 않습니다.") }

                val accessToken = jwtUtil.generateAccessToken(username)
                val refreshToken = jwtUtil.generateRefreshToken(username)

                return ReissueTokenResponse(accessToken, refreshToken)
            } else {
                throw CustomException(HttpStatus.UNAUTHORIZED, "토큰 기한이 만료되었습니다.")
            }
        }
        throw CustomException(HttpStatus.BAD_REQUEST, "지원하지 않은 grant type 입니다.")
    }

}