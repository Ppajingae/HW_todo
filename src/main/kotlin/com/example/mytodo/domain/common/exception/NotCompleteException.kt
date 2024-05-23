package com.example.mytodo.domain.common.exception

data class NotCompleteException(
    val msg: String
):RuntimeException(msg)