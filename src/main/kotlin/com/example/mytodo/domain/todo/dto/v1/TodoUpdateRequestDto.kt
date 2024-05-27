package com.example.mytodo.domain.todo.dto.v1


import java.time.LocalDateTime

data class TodoUpdateRequestDto(

    var title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var complete: Boolean,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    )
