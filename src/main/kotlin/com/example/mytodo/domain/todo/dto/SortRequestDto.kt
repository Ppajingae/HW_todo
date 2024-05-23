package com.example.mytodo.domain.todo.dto

data class SortRequestDto(
    val columnName: String,
    val sortBy: Boolean, // true : asc , false : desc
)
