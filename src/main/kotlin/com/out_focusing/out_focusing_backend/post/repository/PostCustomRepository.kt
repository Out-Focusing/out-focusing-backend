package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.album.domain.Album
import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.domain.Pageable


interface PostCustomRepository {
    fun findPostByPostId(postId: Long, userProfile: UserProfile?): Post
    fun findPostsByPostIds(postIds: List<Long>, userProfile: UserProfile?): List<Post>
    fun findPostsByAlbum(album: Album, pageable: Pageable, userProfile: UserProfile?): List<Post>
    fun fetchJoinPosts(posts: List<Post>)
    fun deleteAllPostHashtagByPost(post: Post)
    fun deleteAllPostContentsByPost(post: Post)
    fun deletePost(post: Post)
}