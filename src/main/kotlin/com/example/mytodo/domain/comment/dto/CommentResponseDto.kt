package com.example.mytodo.domain.comment.dto

import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.user.dto.UserResponseDto


data class CommentResponseDto(

    val id: Long,

    val user: UserResponseDto,

    val comment: String,

    val todo : TodoResponseDto,

):DateTime()