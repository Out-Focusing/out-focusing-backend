package com.out_focusing.out_focusing_backend.user.repository

import com.out_focusing.out_focusing_backend.user.domain.Auth
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository: CrudRepository<Auth, String> {
}