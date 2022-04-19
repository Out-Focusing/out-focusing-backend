package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "post_bookmark")
@SQLDelete(sql = "UPDATE post_bookmark SET deleted = true WHERE post_bookmark_id = ?")
@Where(clause = "deleted=false")
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

    @Column
    val deleted: Boolean = false
}