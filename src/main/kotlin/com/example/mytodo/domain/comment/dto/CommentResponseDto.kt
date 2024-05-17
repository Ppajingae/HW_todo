package com.example.mytodo.domain.comment.dto

import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.user.entity.User


data class CommentResponseDto(

    val id: Long,

    var comment: String,

    )