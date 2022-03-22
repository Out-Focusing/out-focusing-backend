package com.out_focusing.out_focusing_backend.global.error

import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(exception.code.value(), exception.message), exception.code)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.bindingResult.allErrors.get(0).defaultMessage?:""), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(SignatureException::class)
    fun handleSignatureException(exception: SignatureException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "토큰이 변조되었습니다"), HttpStatus.UNAUTHORIZED)
    }
}