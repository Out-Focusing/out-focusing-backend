package com.out_focusing.out_focusing_backend.post.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "post_hashtag")
@SQLDelete(sql = "UPDATE post_hashtag SET deleted = TRUE WHERE post_hashtag_id = ?")
@Where(clause = "deleted=false")
class PostHashtag(
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post,
    @Column(length = 20)
    val hashtag: String,
) {
    @Id
    @Column(name = "post_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val postHashTagId: Long = 0

    @Column
    val deleted: Boolean = false
}