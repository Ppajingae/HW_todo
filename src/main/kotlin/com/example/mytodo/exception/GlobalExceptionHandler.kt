package com.example.mytodo.exception

import com.example.mytodo.exception.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException::class)
    fun idNotFoundException(e: IdNotFoundException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto("Error message: ${e.message}"))
    }
}