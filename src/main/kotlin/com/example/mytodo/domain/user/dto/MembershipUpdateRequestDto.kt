package com.example.mytodo.domain.user.dto

data class MembershipUpdateRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    )
