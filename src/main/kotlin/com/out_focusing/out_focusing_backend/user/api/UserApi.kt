package com.out_focusing.out_focusing_backend.user.api

import com.out_focusing.out_focusing_backend.user.application.UserApplication
import com.out_focusing.out_focusing_backend.user.dto.UserProfileResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User")
@RestController
@RequestMapping("/v1/user")
class UserApi(
    private val userApplication: UserApplication
) {
    @Operation(summary = "Get User Info")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserInfo(@PathVariable userId: String): UserProfileResponse {
        return userApplication.getUserProfile(userId)
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