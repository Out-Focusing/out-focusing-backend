package com.out_focusing.out_focusing_backend.post.domain

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.hibernate.annotations.CreationTimestamp
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
    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_bookmark",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "post_id")]
    )
    val bookmarkUserS: Set<UserProfile>,
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val comments: Set<PostComment>,
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val contents: Set<PostContent>,
    @OneToMany(mappedBy = "postHashTagId.post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val hashtags: Set<PostHashtag>,
    @OneToMany(mappedBy = "postViewsId.post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val postViews: Set<PostViews>
) {
}