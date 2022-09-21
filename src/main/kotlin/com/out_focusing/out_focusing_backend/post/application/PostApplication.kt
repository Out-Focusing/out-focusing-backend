package com.out_focusing.out_focusing_backend.post.application

import com.out_focusing.out_focusing_backend.album.repository.AlbumRepository
import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import com.out_focusing.out_focusing_backend.post.domain.Post
import com.out_focusing.out_focusing_backend.post.domain.PostBookmark
import com.out_focusing.out_focusing_backend.post.domain.PostContent
import com.out_focusing.out_focusing_backend.post.domain.PostHashtag
import com.out_focusing.out_focusing_backend.post.dto.*
import com.out_focusing.out_focusing_backend.post.repository.*
import com.out_focusing.out_focusing_backend.user.repository.UserProfileRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class PostApplication(
    private val postRepository: PostRepository,
    private val postHashtagRepository: PostHashtagRepository,
    private val postContentRepository: PostContentRepository,
    private val postBookmarkRepository: PostBookmarkRepository,
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

    @Transactional
    fun addPostBookmark(postId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }
        val post = postRepository.findPostByPostId(postId, userProfile)

        val postBookmark = PostBookmark(userProfile, post)

        if(postBookmarkRepository.existsPostBookmarkByUserProfileAndPost(userProfile, post)) {
            throw AlreadyAlbumBookmarkedException
        }

        postBookmarkRepository.save(postBookmark)
    }

    @Transactional
    fun cancelPostBookmark(postId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }
        val post = postRepository.findPostByPostId(postId, userProfile)

        if (!postBookmarkRepository.existsPostBookmarkByUserProfileAndPost(userProfile, post)) {
            throw PostBookmarkNotFoundException
        }

        postBookmarkRepository.deletePostBookmarkByUserProfileAndPost(userProfile, post)
    }


    fun searchPostsByKeyword(keyword: String, pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val userId = userDetails.username

        val userProfile = userProfileRepository.findById(userId).orElseThrow { UserNotExistsException }

//        val resultPostIds = esPostRepository.findByKeyword(keyword, pageable).map { it.id }.toList()
        val resultPostIds = listOf<Long>()

        return postRepository.findPostsByPostIds(resultPostIds, userProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }

    }

    fun getPostById(postId: Long): PostDetailResponse {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val userProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        val post = postRepository.findPostByPostId(postId, userProfile)

        return PostDetailResponse.toPostDetailResponse(post, userProfile)
    }

    fun getPostsByAlbum(albumId: Long, pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val userProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        val album = albumRepository.getAlbumDetail(albumId, userProfile) ?: throw AlbumNotFoundException

        return postRepository.findPostsByAlbum(album, pageable, userProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }
    }

    fun getPostsByUser(userId: String, pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val myUserProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        val targetUserProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        return postRepository.findPostsByUserProfile(targetUserProfile, pageable, myUserProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, myUserProfile)
        }
    }

    fun getMyPosts(pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val userProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        return postRepository.findPostsByUserProfile(userProfile, pageable, userProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }
    }

    fun getMyBookmarkPosts(pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val userProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        return postRepository.findPostsByUserBookmark(userProfile, pageable, userProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }
    }

    fun getUserBookmarkPosts(userId: String, pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val myUserProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        val targetUserProfile = userProfileRepository.findById(userId).orElseThrow { UserNotFoundException }

        return postRepository.findPostsByUserBookmark(targetUserProfile, pageable, myUserProfile).map {
            PostSummaryResponse.toPostSummaryResponse(it, myUserProfile)
        }
    }

    fun getPostsMyFeed(pageable: Pageable): List<PostSummaryResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val username = userDetails.username

        val userProfile = userProfileRepository.findById(username).orElseThrow { UserNotExistsException }

        val myFollows = userProfileRepository.getUsersFollowers(userProfile)

        return postRepository.findPostsByUsersAfterDate(myFollows, pageable, userProfile, LocalDateTime.now().minusDays(3)).map {
            PostSummaryResponse.toPostSummaryResponse(it, userProfile)
        }
    }

}