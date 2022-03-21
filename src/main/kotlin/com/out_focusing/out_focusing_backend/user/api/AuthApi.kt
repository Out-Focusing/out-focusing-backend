package com.out_focusing.out_focusing_backend.user.api

import com.out_focusing.out_focusing_backend.user.application.AuthApplication
import com.out_focusing.out_focusing_backend.user.dto.UserRegisterRequest
import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
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

}