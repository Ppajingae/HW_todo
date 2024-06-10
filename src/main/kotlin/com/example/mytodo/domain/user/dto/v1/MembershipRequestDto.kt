package com.example.mytodo.domain.user.dto.v1

data class MembershipRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val admin: Admin = Admin.NORMAL_MEMBER
    )
