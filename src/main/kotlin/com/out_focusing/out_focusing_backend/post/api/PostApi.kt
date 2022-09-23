package com.out_focusing.out_focusing_backend.post.api

import com.out_focusing.out_focusing_backend.post.application.PostApplication
import com.out_focusing.out_focusing_backend.post.dto.request.GeneratePostRequest
import com.out_focusing.out_focusing_backend.post.dto.request.ModifyPostRequest
import com.out_focusing.out_focusing_backend.post.dto.response.GeneratePostResponse
import com.out_focusing.out_focusing_backend.post.dto.response.PostDetailResponse
import com.out_focusing.out_focusing_backend.post.dto.response.PostSummaryResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API")
class PostApi(
    private val postApplication: PostApplication
) {

    @Operation(summary = "게시글 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun generatePost(@RequestBody @Valid requestBody: GeneratePostRequest): GeneratePostResponse {
        return postApplication.generatePost(requestBody)
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun updatePost(@PathVariable postId: Long, @RequestBody @Valid requestBody: ModifyPostRequest) {
        return postApplication.modifyPost(postId, requestBody)
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun removePost(@PathVariable postId: Long) {
        return postApplication.removePost(postId)
    }

    @Operation(summary = "게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun searchPost(@RequestParam(name = "keyword") keyword: String, pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.searchPostsByKeyword(keyword, pageable)
    }

    @Operation(summary = "게시글 단일 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPostById(@PathVariable postId: Long): PostDetailResponse {
        return postApplication.getPostById(postId)
    }

    @Operation(summary = "앨범의 게시글 조회")
    @GetMapping("/albums/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPostsByAlbum(@PathVariable albumId: Long, pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getPostsByAlbum(albumId, pageable)
     }

    @Operation(summary = "유저가 작성한 게시글 조회")
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPostsByUserProfile(@PathVariable userId: String, pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getPostsByUser(userId, pageable)
    }

    @Operation(summary = "유저의 최신 피드 조회")
    @GetMapping("/my/feed")
    @ResponseStatus(HttpStatus.OK)
    fun getPostsMyFeed(pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getPostsMyFeed(pageable)
    }

    @Operation(summary = "자신이 작성한 게시글 조회")
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    fun getMyPosts(pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getMyPosts(pageable)
    }

    @Operation(summary = "자신이 북마크한 게시글 조회")
    @GetMapping("/my/bookmark")
    @ResponseStatus(HttpStatus.OK)
    fun getMyBookmarkPosts(pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getMyBookmarkPosts(pageable)
    }

    @Operation(summary = "유저가 북마크한 게시글 조회")
    @GetMapping("/users/{userId}/bookmark")
    fun getUserBookmarkPosts(@PathVariable userId: String, pageable: Pageable): List<PostSummaryResponse> {
        return postApplication.getUserBookmarkPosts(userId, pageable)
    }

    @Operation(summary = "게시글 북마크")
    @PostMapping("/{postId}/bookmark")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPostBookmark(@PathVariable postId: Long) {
        postApplication.addPostBookmark(postId)
    }

    @Operation(summary = "게시글 북마크 취소")
    @DeleteMapping("/{postId}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    fun cancelPostBookmark(@PathVariable postId: Long) {
        postApplication.cancelPostBookmark(postId)
    }
}