package com.example.mytodo.domain.comment.controller

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo/{todo_id}/comment")
class CommentController(
    private val todoService: TodoService
) {

    @PostMapping
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @RequestBody commentCreateRequestDto: CommentCreateRequestDto)
    :ResponseEntity<CommentResponseDto>{
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createComment(todoId, commentCreateRequestDto))
    }

    @GetMapping
    fun getAllComments(@PathVariable("todo_id") todoId: Long)
    :ResponseEntity<List<CommentResponseDto>>{
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getAllComments(todoId))
    }

    @PutMapping("/{comment_id}")
    fun updateComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @RequestBody commentUpdateRequestDto: CommentUpdateRequestDto
    ):ResponseEntity<CommentResponseDto>{
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateComment(todoId, commentId, commentUpdateRequestDto))
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        ):ResponseEntity<Unit>{
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}