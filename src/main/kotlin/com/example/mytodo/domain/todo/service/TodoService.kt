package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoListResponseDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto


interface TodoService {

    fun getTodo(todoId: Long): TodoResponseDto

    fun getTodoList(): List<TodoListResponseDto>

    fun getTodayTodoList(): List<TodoListResponseDto>

    fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto

    fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto

    fun deleteTodo(todoId: Long)

}