package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import javax.persistence.*

@Entity
@Table(name = "post_comment")
class PostComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val commentId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    val writerUserProfile: UserProfile,
    @Column(length = 3000)
    val content: String
) {
}