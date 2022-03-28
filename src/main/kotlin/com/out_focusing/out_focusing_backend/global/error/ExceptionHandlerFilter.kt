package com.out_focusing.out_focusing_backend.global.error

import com.out_focusing.out_focusing_backend.global.error.CustomException.*
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: CustomException) {
            setErrorResponse(exception.code, response, exception)
        } catch (_: ExpiredJwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, TokenTimeExpiredException)
        } catch (exception: RuntimeException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception)
        }
    }

    fun setErrorResponse(status: HttpStatus, response: HttpServletResponse, exception: Exception) {
        response.status = status.value()
        response.contentType = "application/json"
        val errorResponse = ErrorResponse(response.status, exception.message ?: "")
        response.writer.write(errorResponse.convertToJson())
    }

}