package com.example.mytodo.common.exception

data class StringLengthException(
    val msg: String,
):RuntimeException(msg)