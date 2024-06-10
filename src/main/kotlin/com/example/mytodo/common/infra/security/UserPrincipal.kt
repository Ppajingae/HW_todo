package com.example.mytodo.common.infra.security

import com.example.mytodo.domain.user.dto.v1.Admin
import com.example.mytodo.domain.user.entity.v1.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserPrincipal(
    val id : Long,
    val email: String,
    val authorities: Collection<GrantedAuthority>
){

    constructor(id: Long, email: String, admin: Set<String>) :
            this(id, email, admin.map { SimpleGrantedAuthority("ROLE_$it") })

    fun changeUser(): User {
        val user = User(
            email = email,
            password = "",
            isAdmin = Admin.NORMAL_MEMBER,
            todo = mutableListOf(),
            comment = mutableListOf(),
            nickname = ""
        )

        user.id = id
        return user
    }
}