package com.example.mytodo.domain.user.dto

data class MembershipRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val admin: Admin = Admin.NORMAL_MEMBER
    )
