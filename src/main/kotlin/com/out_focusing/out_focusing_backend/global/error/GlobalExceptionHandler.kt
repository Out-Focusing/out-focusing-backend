package com.out_focusing.out_focusing_backend.global.error

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

}