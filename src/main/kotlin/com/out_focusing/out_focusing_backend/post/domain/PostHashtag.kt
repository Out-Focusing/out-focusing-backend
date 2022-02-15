package com.out_focusing.out_focusing_backend.post.domain

import javax.persistence.*

@Entity
@Table(name = "post_hashtag")
class PostHashtag(
    @EmbeddedId
    val postHashTagId: PostHashtagId
) {
    @Embeddable
    data class PostHashtagId(
        @JoinColumn(name = "post_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val post: Post,
        @Column(length = 20)
        val hashtag: String
    ): java.io.Serializable
}