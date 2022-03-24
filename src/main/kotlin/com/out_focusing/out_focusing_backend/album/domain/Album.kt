package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "album")
class Album(
    @JoinColumn(name = "writer_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val writerUserProfile: UserProfile,
    @Column(length = 40)
    val title: String,
    @Column(length = 300)
    val content: String,
    @Column
    val secret: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    val albumId: Long = 0

    @CreationTimestamp
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime

    @OneToMany(mappedBy = "albumBookmarkId.album",
        fetch = FetchType.LAZY,
        orphanRemoval = true)
    val bookmarkUsers: Set<AlbumBookmark> = setOf()

    @OneToMany(mappedBy = "album", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val posts: Set<Post> = setOf()
}