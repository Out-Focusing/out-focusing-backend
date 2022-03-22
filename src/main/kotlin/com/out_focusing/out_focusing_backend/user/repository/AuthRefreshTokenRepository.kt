package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.AuthRefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthRefreshTokenRepository: CrudRepository<AuthRefreshToken, Long> {

    fun findByRefreshToken(refreshToken: String): Optional<AuthRefreshToken>

}