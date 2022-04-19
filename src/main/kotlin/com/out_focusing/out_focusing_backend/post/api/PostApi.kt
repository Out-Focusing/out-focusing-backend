package com.out_focusing.out_focusing_backend.post.api

import com.out_focusing.out_focusing_backend.post.application.PostApplication
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostRequest
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostResponse
import com.out_focusing.out_focusing_backend.post.dto.ModifyPostRequest
import com.out_focusing.out_focusing_backend.post.dto.PostSummaryResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/post")
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
        return postApplication.searchKeyword(keyword, pageable)
    }


}