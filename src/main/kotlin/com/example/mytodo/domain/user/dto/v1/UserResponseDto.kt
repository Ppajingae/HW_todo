package com.example.mytodo.domain.user.dto.v1

data class UserResponseDto(
    val id: Long,
    val email: String,
    val nickname: String,
    val isAdmin: Admin,
)

