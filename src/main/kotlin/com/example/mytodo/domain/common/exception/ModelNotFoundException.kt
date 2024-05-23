package com.example.mytodo.domain.common.exception

data class ModelNotFoundException (
    val msg: String
): RuntimeException(msg)