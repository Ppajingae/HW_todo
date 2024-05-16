package com.example.mytodo.domain.todo.dto

import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.todo.entity.Todo
import java.time.LocalDateTime

data class TodoResponseDto(

    val id: Long,

    val title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    var createAt: LocalDateTime,

    var updateAt: LocalDateTime,

)