package com.example.mytodo.common.infra.security.jwt

import com.example.mytodo.common.infra.security.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin,
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getBearerToken()

        if(token != null){
            jwtPlugin.validToken(token)
                .onSuccess {
                    val userId = it.payload.subject.toLong()
                    val email = it.payload.get("email", String::class.java)
                    val admin = it.payload.get("admin", String::class.java)
                    val principal = UserPrincipal(
                        id = userId,
                        email = email,
                        admin = setOf(admin))

                    val jwtAuthentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )

                    SecurityContextHolder.getContext().authentication = jwtAuthentication
                }
        }

        filterChain.doFilter(request, response)
    }


    private fun HttpServletRequest.getBearerToken(): String?{

        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null

        return headerValue.substringAfter("Bearer ")
    }
}