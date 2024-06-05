package com.example.mytodo.domain.common.exception

data class StringLengthException(
    val msg: String,
):RuntimeException(msg)