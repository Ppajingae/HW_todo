package com.example.mytodo.domain.comment.controller

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo/{todoId}/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody commentCreateRequestDto: CommentCreateRequestDto)
    :ResponseEntity<CommentResponseDto>{
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(todoId, commentCreateRequestDto))
    }

    @GetMapping
    fun getAllComments(@PathVariable todoId: Long)
    :ResponseEntity<List<CommentResponseDto>>{
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(todoId))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequestDto: CommentUpdateRequestDto
    ):ResponseEntity<CommentResponseDto>{
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(todoId, commentId, commentUpdateRequestDto))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        ):ResponseEntity<Unit>{
        commentService.deleteComment(todoId, commentId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}