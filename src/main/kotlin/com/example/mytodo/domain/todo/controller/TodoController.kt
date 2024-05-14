package com.example.mytodo.domain.todo.controller

import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo")
class TodoController {

    @GetMapping("/{todo_id}")
    fun getTodoById(@PathVariable("todo_id") todoId: Long):TodoResponseDto {
        TODO()
    }

    @GetMapping
    fun getTodoList(): List<TodoResponseDto> {
        TODO()
    }

    @GetMapping("/day")
    fun getTodayToDoList(): List<TodoResponseDto> {
        TODO()
    }

    @PostMapping
    fun createTodo(
        @RequestBody todoCreateRequestDto: TodoCreateRequestDto)
    :TodoResponseDto {
        TODO()
    }

    @PutMapping("/{todo_id}")
    fun updateTodoById(
        @PathVariable("todo_id") todoId: Long,
        @RequestBody todoUpdateRequestDto: TodoUpdateRequestDto):TodoResponseDto {
        TODO()
    }

    @DeleteMapping("/{todo_id}")
    fun deleteTodoById(@PathVariable("todo_id") todoId: Long) {
        TODO()
    }



}