package com.out_focusing.out_focusing_backend.post.api

import com.out_focusing.out_focusing_backend.post.application.PostApplication
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostRequest
import com.out_focusing.out_focusing_backend.post.dto.GeneratePostResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
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


}