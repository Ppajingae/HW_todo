package com.example.mytodo.domain.comment.dto

data class CommentCreateRequestDto(
    val email: String,
    val comment: String,
    val password: String,
)