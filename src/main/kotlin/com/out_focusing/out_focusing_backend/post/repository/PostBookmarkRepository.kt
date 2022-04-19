package com.out_focusing.out_focusing_backend.post.repository

import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.post.domain.PostBookmark
import com.out_focusing.out_focusing_backend.user.domain.UserProfile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostBookmarkRepository: CrudRepository<PostBookmark, Long>{
    fun existsPostBookmarkByUserProfileAndPost(userProfile: UserProfile, post: Post): Boolean
    fun deletePostBookmarkByUserProfileAndPost(userProfile: UserProfile, post: Post)
}