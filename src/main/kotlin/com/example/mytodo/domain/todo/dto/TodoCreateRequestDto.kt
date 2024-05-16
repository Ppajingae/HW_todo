package com.example.mytodo.domain.todo.dto

import com.example.mytodo.domain.user.entity.User
import org.apache.logging.log4j.util.StringMap
import java.time.LocalDateTime

data class TodoCreateRequestDto(

    var title: String,

    var userId: User,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

    )
