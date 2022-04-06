package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE post_id=?")
@Where(clause = "deleted=false")
class Post(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    val writerUserProfile: UserProfile,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    val album: Album,
    @Column
    val secret: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val postId: Long = 0
    @Column
    val deleted: Boolean = false
    @CreationTimestamp
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
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