package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto



interface TodoService {

    fun getTodo(todoId: Long): TodoResponseDto

    fun getTodoList(): List<TodoResponseDto>

    fun getTodayTodoList(): List<TodoResponseDto>

    fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto

    fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto

    fun deleteTodo(todoId: Long)

}