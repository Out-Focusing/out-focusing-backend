package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import javax.persistence.*

@Entity
@Table(name = "post_bookmark")
class PostBookmark(
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val userProfile: UserProfile,

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post
) {
    @Id
    @Column(name = "post_bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postBookmarkId: Long = 0
}