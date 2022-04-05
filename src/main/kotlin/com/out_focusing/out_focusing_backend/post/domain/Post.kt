package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val postId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    val writerUserProfile: UserProfile,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: Album,
    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
    @Column
    val secret: Boolean,
    @Column
    val deleted: Boolean,
) {
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val bookmarkUsers: List<PostBookmark> = listOf()
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val comments: List<PostComment> = listOf()
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val contents: List<PostContent> = listOf()
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val hashtags: List<PostHashtag> = listOf()
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val postViews: List<PostViews> = listOf()

}