package com.out_focusing.out_focusing_backend.user.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_refresh_token")
class AuthRefreshToken(
    @Column(name = "refresh_token", nullable = false)
    val refreshToken: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}