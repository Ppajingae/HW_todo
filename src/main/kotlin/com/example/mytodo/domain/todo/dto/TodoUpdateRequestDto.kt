package com.example.mytodo.domain.todo.dto

import com.example.mytodo.domain.common.DateTime
import org.apache.logging.log4j.util.StringMap
import java.time.LocalDateTime

data class TodoUpdateRequestDto(

    var title: String,

    var todoType: TodoType,

    var importance : Importance,

    var content: String?,

    var startTime: LocalDateTime?,

    var endTime: LocalDateTime,

)
