package com.example.mytodo.domain.todo.dto

import java.time.LocalDateTime

data class TodoCreateRequestDto(

    var title: String,

    var userId: Long,

    var todoType: TodoType,

    var content: String?,

    var importance : Importance,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    )
