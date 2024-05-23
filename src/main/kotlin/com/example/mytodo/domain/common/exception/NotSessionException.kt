package com.example.mytodo.domain.common.exception

data class NotSessionException(
    val errorCode: String,
):RuntimeException(errorCode)
