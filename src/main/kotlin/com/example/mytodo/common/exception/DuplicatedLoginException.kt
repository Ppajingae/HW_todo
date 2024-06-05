package com.example.mytodo.domain.common.exception

data class DuplicatedLoginException(
    val msg: String
):RuntimeException(msg)