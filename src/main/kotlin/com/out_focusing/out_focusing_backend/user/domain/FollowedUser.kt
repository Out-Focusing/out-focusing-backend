package com.out_focusing.out_focusing_backend.user.domain

import javax.persistence.*

@Entity
@Table(name = "followed_user", uniqueConstraints = [UniqueConstraint(columnNames = ["following_user_id", "followed_user_id"])])
class FollowedUser(
    @JoinColumn(name = "following_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val followingUser: UserProfile,
    @JoinColumn(name = "followed_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val followedUser: UserProfile,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val followId: Long = 0
}