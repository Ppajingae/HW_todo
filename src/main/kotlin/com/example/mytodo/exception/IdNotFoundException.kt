package com.example.mytodo.exception


data class IdNotFoundException(
    val msg: String
):RuntimeException("해당하는 리스트가 존재하지 않습니다 애러 내용 : $msg")

