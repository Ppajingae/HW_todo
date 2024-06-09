package com.example.mytodo.common.exception

data class DuplicatedLoginException(
    val msg: String
):RuntimeException(msg)