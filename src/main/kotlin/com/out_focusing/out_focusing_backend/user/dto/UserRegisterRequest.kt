package com.out_focusing.out_focusing_backend.user.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size


data class UserRegisterRequest(
    @field:Size(min = 8, max = 20, message = "아이디의 길이가 8에서 20 사이여야 합니다")
    val userId: String,
    @field:Size(min = 8, max = 255, message = "비밀번호의 길이가 8에서 20 사이여야 합니다")
    val password: String,
    @field:Size(min = 1, max = 20, message = "이름의 길이가 1에서 20 사이여야 합니다")
    val name: String,
    @field:Email(message = "올바른 형식의 이메일 주소여야 합니다")
    val email: String,
    val phoneNumber: String
)
