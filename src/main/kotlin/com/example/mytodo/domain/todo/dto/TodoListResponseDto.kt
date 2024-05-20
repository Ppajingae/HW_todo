package com.example.mytodo.domain.todo.dto

import java.time.LocalDateTime

data class TodoListResponseDto(

    val id: Long,

    val title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var isComplete: Boolean,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    var createAt: LocalDateTime,

    var updateAt: LocalDateTime,

)