package com.out_focusing.out_focusing_backend.global.security

import com.out_focusing.out_focusing_backend.global.error.CustomException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val customUserDetailService: CustomUserDetailService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        authorizationHeader?.let {
            if (it.startsWith("Bearer ")) {
                val accessToken = authorizationHeader.substring(7)
                val username: String
                try {
                    username = jwtUtil.extractUsername(accessToken)
                } catch (exception: SignatureException) {
                    throw CustomException(HttpStatus.UNAUTHORIZED, "토큰이 변조되었습니다.")
                }

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = customUserDetailService.loadUserByUsername(username)

                    if (jwtUtil.validateAccessToken(accessToken, userDetails)) {
                        val usernamePasswordAuthenticationToken =
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                        usernamePasswordAuthenticationToken.details =
                            WebAuthenticationDetailsSource().buildDetails(request)

                        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                    }
                }
            }
        }
        filterChain.doFilter(request, response)
    }


}