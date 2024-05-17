package com.example.mytodo.domain.exception

data class NoAuthorityException(
    val msg: String
):RuntimeException("권한이 없습 니다")