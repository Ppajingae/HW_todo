package com.example.mytodo.domain.exception

data class ModelNotFoundException (
    val msg: String
): RuntimeException(msg)