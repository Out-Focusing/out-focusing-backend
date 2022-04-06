package com.out_focusing.out_focusing_backend.post.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "post_content")
@SQLDelete(sql = "UPDATE post_content SET deleted = TRUE WHERE content_id")
@Where(clause = "deleted=false")
class PostContent(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,
    @Column(name = "fileUrl", length = 100)
    val fileUrl: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    val contentId: Long = 0

    @Column
    val deleted: Boolean = false
}