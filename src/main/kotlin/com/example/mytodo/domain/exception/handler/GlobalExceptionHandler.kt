package com.example.mytodo.domain.exception.handler

import com.example.mytodo.domain.exception.*
import com.example.mytodo.domain.exception.dto.ErrorResponseDto
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

    @ExceptionHandler(NoAuthorityException::class)
    fun noAuthorityException(e: Exception): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponseDto("not Authority: ${e.message}"))
    }

    @ExceptionHandler(NotCompleteException::class)
    fun notCompleteException(e: Exception): ResponseEntity<ErrorResponseDto>{
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponseDto("this todo is not complete : ${e.message}"))
    }

    @ExceptionHandler(ModelNotFoundException::class)
    fun modelNotFoundException(e: Exception): ResponseEntity<ErrorResponseDto>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto("Model Not Found: ${e.message}"))
    }

    @ExceptionHandler(StringLengthException::class)
    fun stringLengthException(e: Exception): ResponseEntity<ErrorResponseDto>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto("Length of Error: ${e.message}"))
    }
}