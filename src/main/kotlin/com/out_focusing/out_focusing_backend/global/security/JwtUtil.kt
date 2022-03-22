package com.out_focusing.out_focusing_backend.global.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    @Value("\${out_focusing.access_token_secret_key}")
    private lateinit var accessTokenSecretKey: String

    @Value("\${out_focusing.refresh_token_secret_key}")
    private lateinit var refreshSecretKey: String

    companion object {
        private const val accessTokenExpireTime = 1000 * 20
        private const val refreshTokenExpireTime = 1000 * 60 * 60 * 24 * 7
    }

    fun getSignInKey(secretKey: String): Key {
        val keyByteArray = secretKey.toByteArray()
        return Keys.hmacShaKeyFor(keyByteArray)
    }

    fun extractUsername(token: String, isAccessToken: Boolean = true): String =
        extractClaim(token, Claims::getSubject, if (isAccessToken) accessTokenSecretKey else refreshSecretKey)

    fun extractExpiration(token: String, isAccessToken: Boolean = true): Date =
        extractClaim(token, Claims::getExpiration, if (isAccessToken) accessTokenSecretKey else refreshSecretKey)

    fun <T> extractClaim(token: String, claimsResolver: java.util.function.Function<Claims, T>, key: String) =
        claimsResolver.apply(extractAllClaims(token, key))

    private fun extractAllClaims(token: String, key: String) =
        Jwts.parserBuilder().setSigningKey(getSignInKey(key)).build().parseClaimsJws(token).body

    private fun isAccessTokenExpired(token: String) = extractExpiration(token).before(Date())

    fun isRefreshTokenExpired(refreshToken: String) = extractExpiration(refreshToken, false).before(Date())

    fun generateAccessToken(username: String) = createAccessToken(emptyMap(), username)

    fun createAccessToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + accessTokenExpireTime))
            .signWith(getSignInKey(accessTokenSecretKey), SignatureAlgorithm.HS256).compact()
    }

    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpireTime))
            .signWith(getSignInKey(refreshSecretKey))
            .compact()
    }

    fun validateAccessToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isAccessTokenExpired(token)
    }

}