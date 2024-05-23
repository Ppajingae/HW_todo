package com.example.mytodo.domain.common.exception

data class NoAuthorityException(
    val msg: String
):RuntimeException(msg)