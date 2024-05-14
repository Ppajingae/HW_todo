package com.example.mytodo.common

import java.time.LocalDateTime

open class DateTime {
    val createTime: LocalDateTime = LocalDateTime.now()
    val updateTime: LocalDateTime = LocalDateTime.now()
}