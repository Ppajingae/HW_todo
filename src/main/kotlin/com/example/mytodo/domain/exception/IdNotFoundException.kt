package com.example.mytodo.domain.exception


data class IdNotFoundException(
    val msg: String
):RuntimeException("아이디가 존재하지 않습니다 애러 내용 : $msg")

