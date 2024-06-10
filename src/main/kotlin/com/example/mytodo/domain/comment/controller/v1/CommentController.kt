package com.example.mytodo.domain.comment.controller.v1

import com.example.mytodo.domain.comment.dto.v1.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.v1.CommentDeleteRequestDto
import com.example.mytodo.domain.comment.dto.v1.CommentResponseDto
import com.example.mytodo.domain.comment.dto.v1.CommentUpdateRequestDto
import com.example.mytodo.domain.comment.service.v1.CommentService
import com.example.mytodo.domain.common.ApiV1Config
import com.example.mytodo.domain.common.dto.DeleteResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo/{todoId}/comment")
class CommentController(
    private val commentService: CommentService
):ApiV1Config() {

    @PostMapping
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody commentCreateRequestDto: CommentCreateRequestDto
    )
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
        @RequestBody commentDeleteRequestDto: CommentDeleteRequestDto,
        ):ResponseEntity<DeleteResponseDto>{

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(todoId, commentId, commentDeleteRequestDto))
    }
}