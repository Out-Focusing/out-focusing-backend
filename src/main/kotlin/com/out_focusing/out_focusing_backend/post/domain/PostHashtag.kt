package com.out_focusing.out_focusing_backend.post.domain

import javax.persistence.*

@Entity
@Table(name = "post_hashtag")
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
}