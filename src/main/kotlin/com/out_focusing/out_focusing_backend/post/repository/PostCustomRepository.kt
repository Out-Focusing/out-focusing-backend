package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime


interface PostCustomRepository {
    fun findPostByPostId(postId: Long, userProfile: UserProfile?): Post
    fun findPostsByPostIds(postIds: List<Long>, userProfile: UserProfile?): List<Post>
    fun findPostsByAlbum(album: Album, pageable: Pageable, userProfile: UserProfile?): List<Post>
    fun findPostsByUserProfile(targetUserProfile: UserProfile, pageable: Pageable, myUserProfile: UserProfile?): List<Post>
    fun findPostsByUserBookmark(targetUserProfile: UserProfile, pageable: Pageable, myUserProfile: UserProfile?): List<Post>
    fun findPostsByUsersAfterDate(userProfiles: List<UserProfile>, pageable: Pageable, myUserProfile: UserProfile?, date: LocalDateTime): List<Post>
    fun searchPostsByKeyword(keyword: String, pageable: Pageable, userProfile: UserProfile?): List<Post>
    fun fetchJoinPosts(posts: List<Post>)
    fun deleteAllPostHashtagByPost(post: Post)
    fun deleteAllPostContentsByPost(post: Post)
    fun deletePost(post: Post)
}