package com.example.mytodo.domain.todo.controller

import com.example.mytodo.domain.common.exception.StringLengthException
import com.example.mytodo.domain.todo.dto.*
import com.example.mytodo.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
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

    @GetMapping("/admin/{correctionId}")
    fun getTodoList(
        @PathVariable correctionId: Long,
    ): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoList(correctionId))
    }

    @GetMapping("/day")
    fun getTodayToDoList(): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodayTodoList())
    }

    @PostMapping("/sort")
    fun getTodoListSorted(
        @RequestBody sortRequestDto: SortRequestDto
    ): ResponseEntity<List<TodoListResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoListSorted(sortRequestDto))
    }

    @GetMapping("/test/{userId}")
    fun getUserTodoList(@PathVariable userId: Long): ResponseEntity<List<TodoListResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getUserTodoList(userId))
    }


    @PostMapping
    fun createTodo(
        @Valid @RequestBody todoCreateRequestDto: TodoCreateRequestDto,
        bindingResult: BindingResult
    ):ResponseEntity<TodoResponseDto>  {
        if(bindingResult.hasErrors()){

           throw StringLengthException(bindingResult.fieldError?.defaultMessage.toString())
        }

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