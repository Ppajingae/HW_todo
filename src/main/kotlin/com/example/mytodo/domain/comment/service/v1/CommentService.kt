package com.example.mytodo.domain.comment.service.v1

import com.example.mytodo.domain.common.dto.DeleteResponseDto

interface CommentService {
    fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto

    fun getAllComments(todoId: Long): List<CommentResponseDto>

    fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto

    fun deleteComment(todoId: Long, commentId: Long, commentDeleteRequestDto: CommentDeleteRequestDto): DeleteResponseDto
}