package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "post_comment")
@SQLDelete(sql = "UPDATE post_comment SET deleted = true WHERE comment_id = ?")
@Where(clause = "deleted=false")
class PostComment(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    val writerUserProfile: UserProfile,
    @Column(length = 3000)
    val content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val commentId: Long = 0

    @Column
    val deleted: Boolean = false
}