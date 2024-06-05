package com.example.mytodo.common.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*
import java.time.Duration

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
) {

    fun validToken(jwt: String): Result<Jws<Claims>>{
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }

    }

    fun generateAccessToken(subject: String, email: String, role: String):String{

        return generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour))
    }

    private fun generateToken(subject: String, email: String, role: String, expirationPeriod : Duration): String{

         val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
         val now = Instant.now()
         val claims: Claims = Jwts.claims()
             .add(mapOf("email" to email, "role" to role))
             .build()


        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}