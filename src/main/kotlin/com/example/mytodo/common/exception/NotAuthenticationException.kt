package com.example.mytodo.common.exception

data class NotAuthenticationException(
    val errorCode: String,
):RuntimeException(errorCode)
