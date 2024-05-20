package com.example.mytodo.domain.todo.controller

import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoListResponseDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto
import com.example.mytodo.domain.todo.service.TodoService
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long):ResponseEntity<TodoResponseDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(todoId))
    }

    @GetMapping
    fun getTodoList(): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoList())
    }

    @GetMapping("/day")
    fun getTodayToDoList(): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodayTodoList())
    }

    @PostMapping
    fun createTodo(
        @RequestBody todoCreateRequestDto: TodoCreateRequestDto)
    :ResponseEntity<TodoResponseDto>  {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoCreateRequestDto))
    }

    @PutMapping("/{todoId}")
    fun updateTodoById(
        @PathVariable todoId: Long,
        @RequestBody todoUpdateRequestDto: TodoUpdateRequestDto):ResponseEntity<TodoResponseDto>  {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(todoId, todoUpdateRequestDto))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodoById(@PathVariable todoId: Long):ResponseEntity<Unit>  {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(todoService.deleteTodo(todoId))
    }

}