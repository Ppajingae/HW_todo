package com.example.mytodo.common.exception

data class NotSessionException(
    val errorCode: String,
):RuntimeException(errorCode)
