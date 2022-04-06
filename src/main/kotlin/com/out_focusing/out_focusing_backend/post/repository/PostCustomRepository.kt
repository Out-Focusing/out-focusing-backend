package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.user.domain.UserProfile


interface PostCustomRepository {
    fun findPostByPostId(postId: Long, userProfile: UserProfile?): Post
    fun deleteAllPostHashtagByPost(post: Post)
    fun deleteAllPostContentsByPost(post: Post)
    fun deletePost(post: Post)
}