package com.example.mytodo.domain.user.dto

data class UserResponseDto(
    val id: Long,
    val email: String,
    val nickname: String,
    val isAdmin: Admin,
)

