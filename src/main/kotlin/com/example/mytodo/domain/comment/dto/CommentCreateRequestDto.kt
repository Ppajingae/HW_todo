package com.example.mytodo.domain.comment.dto

data class CommentCreateRequestDto(
    val userId: Long,
    val comment: String,
)