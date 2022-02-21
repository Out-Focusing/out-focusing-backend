package com.out_focusing.out_focusing_backend.user.domain

import javax.persistence.*

@Entity
@Table(name = "auth")
class Auth(
    @Id
    val userId: String,
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    val userProfile: UserProfile,
    @Column(length = 20)
    val password: String
) {
}