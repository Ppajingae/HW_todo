package com.example.mytodo.domain.user.dto

data class MembershipUpdateAdminRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val isAdmin: Admin
    )
