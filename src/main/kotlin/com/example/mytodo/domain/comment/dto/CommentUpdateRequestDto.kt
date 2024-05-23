package com.example.mytodo.domain.comment.dto

data class CommentUpdateRequestDto(
    val userId: Long,
    val comment: String,
)
