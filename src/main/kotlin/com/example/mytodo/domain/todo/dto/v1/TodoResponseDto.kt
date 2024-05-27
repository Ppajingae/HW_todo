package com.example.mytodo.domain.todo.dto.v1

import com.example.mytodo.domain.comment.entity.v1.Comment
import java.time.LocalDateTime

data class TodoResponseDto(

    val id: Long,

    val title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var complete: Boolean,

    var comment: MutableList<Comment>,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    var createAt: LocalDateTime,

    var updateAt: LocalDateTime,

    )