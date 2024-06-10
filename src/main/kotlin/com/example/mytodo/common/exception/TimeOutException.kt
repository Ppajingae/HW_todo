package com.example.mytodo.common.exception

data class TimeOutException(
    val msg: String
): RuntimeException(msg)
