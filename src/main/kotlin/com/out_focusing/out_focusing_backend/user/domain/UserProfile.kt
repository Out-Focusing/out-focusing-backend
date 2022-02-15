package com.out_focusing.out_focusing_backend.user.domain

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.post.domain.Post
import javax.persistence.*

@Entity
@Table(name = "user_profile")
class UserProfile(
    @Id
    val userId: String,
    @Column(length = 20)
    val name: String,
    @Column(length = 30)
    val contact: String,
    @Column(length = 100)
    val profileImage: String,
    @Column(length = 255)
    val readme: String,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "followed_user",
        joinColumns = [JoinColumn(name = "following_id")],
        inverseJoinColumns = [JoinColumn(name = "followed_id")]
    )
    val followedUsers: Set<UserProfile>,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "followed_user",
        joinColumns = [JoinColumn(name = "followed_id")],
        inverseJoinColumns = [JoinColumn(name = "following_id")]
    )
    val followingUsers: Set<UserProfile>,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "blocked_user",
        joinColumns = [JoinColumn(name = "blocking_id")],
        inverseJoinColumns = [JoinColumn(name = "blocked_id")]
    )
    val blockedUsers: Set<UserProfile>,
    @OneToMany(mappedBy = "writerUserProfile", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val albums: Set<Album>,
    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "album_bookmark",
        joinColumns = [JoinColumn(name = "album_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val bookmarkAlbums: Set<Album>,
    @OneToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY, mappedBy = "writerUserProfile")
    val posts: Set<Post>,
    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_bookmark",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val bookmarkPost: Set<Post>
) {

}