package com.out_focusing.out_focusing_backend.user.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User")
@RestController
@RequestMapping("/v1/user")
class UserApi {
    @Operation(summary = "Get User Info")
    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable userId: String) {
        // TODO
    }

    @Operation(summary = "Get My Info")
    @GetMapping("/my")
    fun getMyInfo() {
        // TODO
    }

    @Operation(summary = "Get User's Followers")
    @GetMapping("/{userId}/followers")
    fun getMyFollowers(@PathVariable userId: String) {
       // TODO
    }

    @Operation(summary  = "Get User's Followings")
    @GetMapping("/{userId}/followings")
    fun getMyFollowings(@PathVariable userId: String) {
        // TODO
    }
}