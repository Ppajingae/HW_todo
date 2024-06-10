package com.example.mytodo.common.exception

data class NotCompleteException(
    val msg: String
):RuntimeException(msg)