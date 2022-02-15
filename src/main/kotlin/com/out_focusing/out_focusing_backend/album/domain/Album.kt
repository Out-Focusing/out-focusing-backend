package com.out_focusing.out_focusing_backend.album.domain

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "album")
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    val albumId: Long,
    @JoinColumn(name = "writer_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val writerUserProfile: UserProfile,
    @Column(length = 40)
    val title: String,
    @Column(length = 300)
    val content: String,
    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
    @Column
    val secret: Boolean,
    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "album_bookmark",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "album_id")]
    )
    val bookmarkUsers: Set<UserProfile>,
    @OneToMany(mappedBy = "album", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val posts: Set<Post>
) {

}