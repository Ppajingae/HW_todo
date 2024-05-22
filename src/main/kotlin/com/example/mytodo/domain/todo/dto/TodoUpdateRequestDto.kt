package com.example.mytodo.domain.todo.dto


import jakarta.validation.Valid
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
