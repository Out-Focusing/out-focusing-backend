package com.out_focusing.out_focusing_backend.user.api

import com.out_focusing.out_focusing_backend.user.application.AuthApplication
import com.out_focusing.out_focusing_backend.user.dto.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthApi(private val authApplication: AuthApplication) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid requestBody: UserRegisterRequest) {
        authApplication.registerUser(requestBody)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid requestBody: UserLoginRequest): UserLoginResponse {
        return authApplication.loginUser(requestBody)
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    fun reissueToken(@RequestBody @Valid requestBody: ReissueTokenRequest): ReissueTokenResponse {
        return authApplication.reissueToken(requestBody)
    }

}