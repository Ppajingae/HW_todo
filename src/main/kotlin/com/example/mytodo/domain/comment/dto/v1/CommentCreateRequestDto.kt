package com.example.mytodo.domain.comment.dto.v1

data class CommentCreateRequestDto(
    val userId: Long,
    val comment: String,
)