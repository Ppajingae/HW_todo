package com.example.mytodo.common.exception

data class ModelNotFoundException (
    val msg: String
): RuntimeException(msg)