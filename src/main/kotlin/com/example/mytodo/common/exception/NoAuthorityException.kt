package com.example.mytodo.common.exception

data class NoAuthorityException(
    val msg: String
):RuntimeException(msg)