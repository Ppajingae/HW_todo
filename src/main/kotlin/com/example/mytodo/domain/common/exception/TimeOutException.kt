package com.example.mytodo.domain.common.exception

data class TimeOutException(
    val msg: String
): RuntimeException(msg)
