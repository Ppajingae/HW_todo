package com.example.mytodo.domain.todo.dto.v1


import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class TodoUpdateRequestDto(

    @field:Size(min = 1, max = 200, message = "최소 1자 이상 200자 까지 입력 가능 합니다")
    var title: String,

    var todoType: TodoType,

    var importance : Importance,

    @field:Size(min = 1, max = 1000, message = "최소 1자 이상 1000자 까지 입력 가능 합니다")
    var content: String?,

    var complete: Boolean,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    )
