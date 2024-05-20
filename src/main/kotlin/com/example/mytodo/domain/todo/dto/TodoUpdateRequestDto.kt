package com.example.mytodo.domain.todo.dto


import java.time.LocalDateTime

data class TodoUpdateRequestDto(

    var title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var isComplete: Boolean,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

)
