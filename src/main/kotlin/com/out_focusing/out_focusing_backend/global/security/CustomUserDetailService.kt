package com.out_focusing.out_focusing_backend.global.security

import com.out_focusing.out_focusing_backend.global.error.CustomException
import com.out_focusing.out_focusing_backend.user.repository.AuthRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val authRepository: AuthRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        val auth  = authRepository.findById(username).orElseThrow { CustomException(HttpStatus.UNAUTHORIZED, "로그인 정보가 존재하지 않습니다.") }

        return User(auth.userId, auth.password, listOf())
    }
}