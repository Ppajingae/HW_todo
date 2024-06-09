package com.example.mytodo.domain.todo.controller.v1

import com.example.mytodo.common.exception.StringLengthException
import com.example.mytodo.domain.todo.dto.v1.*
import com.example.mytodo.domain.todo.service.v1.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @GetMapping("/{todoId}")
    fun getTodoById(@PathVariable todoId: Long):ResponseEntity<TodoResponseDto> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(todoId))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    fun getTodoList(
    ): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoList())
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @GetMapping("/day")
    fun getTodayToDoList(): ResponseEntity<List<TodoListResponseDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodayTodoList())
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @PostMapping("/sort")
    fun getTodoListSorted(
        @RequestBody sortRequestDto: SortRequestDto
    ): ResponseEntity<List<TodoListResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoListSorted(sortRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @GetMapping("/test/{userId}")
    fun getUserTodoList(@PathVariable userId: Long): ResponseEntity<List<TodoListResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getUserTodoList(userId))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @PutMapping("/{todoId}")
    fun updateTodoById(
        @PathVariable todoId: Long,
        @Valid @RequestBody todoUpdateRequestDto: TodoUpdateRequestDto,
        bindingResult: BindingResult
    ):ResponseEntity<TodoResponseDto>  {
        if(bindingResult.hasErrors()){
            throw StringLengthException(bindingResult.fieldError?.defaultMessage.toString())
        }

        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(todoId, todoUpdateRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL_MEMBER')")
    @DeleteMapping("/{todoId}")
    fun deleteTodoById(@PathVariable todoId: Long):ResponseEntity<Unit>  {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(todoService.deleteTodo(todoId))
    }

}