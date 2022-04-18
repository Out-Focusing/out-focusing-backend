package com.out_focusing.out_focusing_backend.post.application

import com.out_focusing.out_focusing_backend.album.repository.AlbumRepository
import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.post.domain.PostContent
import com.out_focusing.out_focusing_backend.post.domain.PostHashtag
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostResponse
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostRequest
import com.out_focusing.out_focusing_backend.post.dto.ModifyPostRequest
import com.out_focusing.out_focusing_backend.post.dto.PostSummaryResponse
import com.out_focusing.out_focusing_backend.post.repository.ESPostRepository
import com.out_focusing.out_focusing_backend.post.repository.PostContentRepository
import com.out_focusing.out_focusing_backend.post.repository.PostHashtagRepository
import com.out_focusing.out_focusing_backend.post.repository.PostRepository
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostApplication(
    private val postRepository: PostRepository,
    private val postHashtagRepository: PostHashtagRepository,
    private val postContentRepository: PostContentRepository,
    private val esPostRepository: ESPostRepository,
    private val albumRepository: AlbumRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    @Transactional
    fun generatePost(requestBody: GeneratePostRequest): GeneratePostResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        val album = albumRepository.getAlbumDetail(requestBody.albumId, userProfile) ?: throw AlbumNotFoundException

        val generationPost = Post(
            writerUserProfile = userProfile,
            album = album,
            secret = requestBody.isSecret
        )

        postRepository.save(generationPost)

        postHashtagRepository.saveAll(requestBody.hashtags.map { PostHashtag(generationPost, it) })
        postContentRepository.saveAll(requestBody.contents.map { PostContent(generationPost, it) })


        return GeneratePostResponse(generationPost.postId)
    }

    @Transactional
    fun modifyPost(postId: Long, requestBody: ModifyPostRequest) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        val post = postRepository.findPostByPostId(postId, userProfile)

        if (post.writerUserProfile != userProfile) throw PostUpdateForbiddenException

        val album = albumRepository.getAlbumDetail(requestBody.albumId, userProfile) ?: throw AlbumNotFoundException

        postRepository.deleteAllPostHashtagByPost(post)
        postHashtagRepository.saveAll(requestBody.hashtags.map { PostHashtag(post, it) })

        postRepository.deleteAllPostContentsByPost(post)
        postContentRepository.saveAll(requestBody.contents.map { PostContent(post, it) })

        post.album = album
        post.secret = requestBody.isSecret
    }

    @Transactional
    fun removePost(postId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        val post = postRepository.findPostByPostId(postId, userProfile)

        if (post.writerUserProfile != userProfile) throw PostDeleteForbiddenException

        postRepository.deletePost(post)
    }

    fun searchKeyword(keyword: String): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

        val resultPostIds = esPostRepository.findByKeyword(keyword).map { it.id }

        return postRepository.findPostsByPostIds(resultPostIds, userProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }

    }

}