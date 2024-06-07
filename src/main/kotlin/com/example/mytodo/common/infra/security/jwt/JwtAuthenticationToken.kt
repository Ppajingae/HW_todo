package com.example.mytodo.common.infra.security.jwt

import com.example.mytodo.common.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails


class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    details: WebAuthenticationDetails,
) : AbstractAuthenticationToken(principal.authorities) {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal
}