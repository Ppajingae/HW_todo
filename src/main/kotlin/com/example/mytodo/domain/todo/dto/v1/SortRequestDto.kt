package com.example.mytodo.domain.todo.dto.v1

data class SortRequestDto(
    val columnName: String,
    val sortBy: Boolean, // true : asc , false : desc
)
