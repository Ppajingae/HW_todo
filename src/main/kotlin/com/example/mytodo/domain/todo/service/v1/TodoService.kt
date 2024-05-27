package com.example.mytodo.domain.todo.service.v1

import com.example.mytodo.domain.todo.dto.v1.*


interface TodoService {

    fun getTodo(todoId: Long): TodoResponseDto

    fun getTodoList(correctionId: Long): List<TodoListResponseDto>

    fun getTodayTodoList(): List<TodoListResponseDto>

    fun getUserTodoList(userId: Long): List<TodoListResponseDto>

    fun getTodoListSorted(sortRequestDto: SortRequestDto): List<TodoListResponseDto>

    fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto

    fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto

    fun deleteTodo(todoId: Long)

}