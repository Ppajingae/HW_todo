package com.example.mytodo.domain.exception

data class NoAuthorityException(
    val msg: String
):RuntimeException(msg)