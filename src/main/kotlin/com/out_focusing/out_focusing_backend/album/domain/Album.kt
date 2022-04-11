package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "album")
@SQLDelete(sql = "UPDATE album SET deleted = true WHERE album_id=?")
@Where(clause = "deleted=false")
class Album(
    @JoinColumn(name = "writer_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val writerUserProfile: UserProfile,
    @Column(length = 40)
    var title: String,
    @Column(length = 300)
    var content: String,
    @Column
    var secret: Boolean,
    @Column
    var thumbnail: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    val albumId: Long = 0

    @Column
    var deleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime

    @UpdateTimestamp
    @Column(name = "modified_at")
    lateinit var modifiedAt: LocalDateTime

    @OneToMany(
        mappedBy = "album",
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val bookmarkUsers: List<AlbumBookmark> = listOf()

    @OneToMany(mappedBy = "album", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val posts: List<Post> = listOf()
}