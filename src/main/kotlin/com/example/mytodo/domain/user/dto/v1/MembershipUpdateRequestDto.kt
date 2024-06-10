package com.example.mytodo.domain.user.dto.v1

data class MembershipUpdateRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    )
