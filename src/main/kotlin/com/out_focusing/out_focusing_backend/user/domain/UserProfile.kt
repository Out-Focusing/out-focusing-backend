package com.out_focusing.out_focusing_backend.user.domain

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.album.domain.AlbumBookmark
import com.out_focusing.out_focusing_backend.post.domain.Post
import javax.persistence.*

@Entity
@Table(name = "user_profile")
class UserProfile(
    @Id
    @Column(name = "user_id")
    val userId: String,
    @Column(length = 20)
    var name: String,
    @Column(length = 30)
    var contact: String,
    @Column(name = "profile_image", length = 100)
    var profileImage: String,
    @Column(length = 255)
    var readme: String,
    @OneToMany(mappedBy = "followedUser", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val followedUsers: List<FollowedUser>,
    @OneToMany(mappedBy = "followingUser", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val followingUsers: List<FollowedUser>,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "blocked_user",
        joinColumns = [JoinColumn(name = "blocking_id")],
        inverseJoinColumns = [JoinColumn(name = "blocked_id")]
    )
    val blockedUsers: List<UserProfile>,
    @OneToMany(mappedBy = "writerUserProfile", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val albums: List<Album>,
    @OneToMany(mappedBy = "userProfile", fetch = FetchType.LAZY)
    val bookmarkAlbums: List<AlbumBookmark>,
    @OneToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY, mappedBy = "writerUserProfile")
    val posts: List<Post>,
    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_bookmark",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val bookmarkPost: List<Post>
) {

}