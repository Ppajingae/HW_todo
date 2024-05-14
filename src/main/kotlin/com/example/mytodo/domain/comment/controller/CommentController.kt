package com.example.mytodo.domain.comment.controller

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/todo/{todo_id}/comment")
class CommentController {

    @PostMapping
    fun createComment(
        @PathVariable("todo_id") todoId: Long,
        @RequestBody commentCreateRequestDto: CommentCreateRequestDto)
    :CommentResponseDto{
        TODO()
    }

    @GetMapping
    fun getAllComments(@PathVariable("todo_id") todoId: Long)
    :List<CommentResponseDto>{
        TODO()
    }

    @PutMapping("/{comment_id}")
    fun updateComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        @RequestBody commentUpdateRequestDto: CommentUpdateRequestDto
    ):CommentResponseDto{
        TODO()
    }

    @DeleteMapping("/{comment_id}")
    fun deleteComment(
        @PathVariable("todo_id") todoId: Long,
        @PathVariable("comment_id") commentId: Long,
        ){
        TODO()
    }
}