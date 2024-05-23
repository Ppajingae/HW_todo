package com.example.mytodo.domain.todo.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class TodoCreateRequestDto(

    @field:Size(min = 1, max = 200, message = "최소 1자 이상 200자 까지 입력 가능 합니다")
    var title: String,

    var userId: Long,

    var todoType: TodoType,

    @field:Size(min = 1, max = 1000, message = "최소 1자 이상 1000자 까지 입력 가능 합니다")
    var content: String?,

    var importance : Importance,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    )
