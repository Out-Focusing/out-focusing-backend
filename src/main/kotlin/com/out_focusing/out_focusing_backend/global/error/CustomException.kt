package com.out_focusing.out_focusing_backend.global.error

import org.springframework.http.HttpStatus

class CustomException(val code: HttpStatus, override val message: String): RuntimeException() {
}