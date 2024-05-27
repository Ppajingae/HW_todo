package com.example.mytodo.domain.comment.dto.v1

data class CommentUpdateRequestDto(
    val userId: Long,
    val comment: String,
)
