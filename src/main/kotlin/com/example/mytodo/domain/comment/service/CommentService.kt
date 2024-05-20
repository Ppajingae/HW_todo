package com.example.mytodo.domain.comment.service

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentDeleteRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.common.DeleteResponseDto

interface CommentService {
    fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto

    fun getAllComments(todoId: Long): List<CommentResponseDto>

    fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto

    fun deleteComment(todoId: Long, commentId: Long, commentDeleteRequestDto: CommentDeleteRequestDto): DeleteResponseDto
}