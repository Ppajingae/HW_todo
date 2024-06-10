package com.example.mytodo.common.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserPrincipal(
    val id : Long,
    val email: String,
    val authorities: Collection<GrantedAuthority>
){

    constructor(id: Long, email: String, admin: Set<String>) :
            this(id, email, admin.map { SimpleGrantedAuthority("ROLE_$it") })
}