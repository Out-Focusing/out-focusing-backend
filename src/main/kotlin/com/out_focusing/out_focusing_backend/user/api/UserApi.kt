package com.out_focusing.out_focusing_backend.user.api

import com.out_focusing.out_focusing_backend.user.application.UserApplication
import com.out_focusing.out_focusing_backend.user.dto.ModifyUserProfileRequest
import com.out_focusing.out_focusing_backend.user.dto.UserProfileResponse
import com.out_focusing.out_focusing_backend.user.dto.UserProfileSummaryResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "User")
@RestController
@RequestMapping("/v1/user")
class UserApi(
    private val userApplication: UserApplication
) {
    @Operation(summary = "Modify User Info")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyUserProfile(@RequestBody @Valid request: ModifyUserProfileRequest) {
        userApplication.modifyUserProfile(request)
    }

    @Operation(summary = "Get User Info")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserInfo(@PathVariable userId: String): UserProfileResponse {
        return userApplication.getUserProfile(userId)
    }

    @Operation(summary = "Get My Info")
    @GetMapping("/my")
    fun getMyInfo(): UserProfileResponse {
        return userApplication.getMyProfile()
    }

    @Operation(summary = "Get User's Followers")
    @GetMapping("/{userId}/followers")
    fun getUsersFollowers(@PathVariable userId: String): List<UserProfileSummaryResponse> {
        return userApplication.getUsersFollowers(userId)
    }

    @Operation(summary = "Get User's Followings")
    @GetMapping("/{userId}/followings")
    fun getUsersFollowings(@PathVariable userId: String): List<UserProfileSummaryResponse> {
        return userApplication.getUsersFollowings(userId)
    }

    @Operation(summary = "Follow User")
    @PostMapping("/follow/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun followUser(@PathVariable userId: String) {
        userApplication.followUser(userId)
    }

    @Operation(summary = "Unfollow User")
    @DeleteMapping("/unfollow/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun unfollowUser(@PathVariable userId: String) {
        userApplication.unfollowUser(userId)
    }
}