package com.example.mytodo.domain.exception

data class NotCompleteException(
    val msg: String
):RuntimeException(msg)