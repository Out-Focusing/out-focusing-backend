package com.out_focusing.out_focusing_backend.user.domain

import javax.persistence.*

@Entity
@Table(name = "auth")
class Auth(
    @Id
    val userId: String,
    @MapsId
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val userProfile: UserProfile,
    @Column(length = 255)
    val password: String
) {
}