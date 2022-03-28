package com.out_focusing.out_focusing_backend.global.security

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.user.repository.AuthRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(private val authRepository: AuthRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        val auth  = authRepository.findById(username).orElseThrow { UserNotExistsException }

        return User(auth.userId, auth.password, listOf())
    }
}