package com.out_focusing.out_focusing_backend.user.api

import com.out_focusing.out_focusing_backend.user.application.AuthApplication
import com.out_focusing.out_focusing_backend.user.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "Auth API")
@RestController
@RequestMapping("/v1/auth")
class AuthApi(private val authApplication: AuthApplication) {

    @Operation(summary = "회원가입")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid requestBody: UserRegisterRequest) {
        authApplication.registerUser(requestBody)
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid requestBody: UserLoginRequest): UserLoginResponse {
        return authApplication.loginUser(requestBody)
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    fun reissueToken(@RequestBody @Valid requestBody: ReissueTokenRequest): ReissueTokenResponse {
        return authApplication.reissueToken(requestBody)
    }

}