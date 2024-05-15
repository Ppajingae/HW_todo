package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl: TodoService {

    override fun getTodo(todoId: Long): TodoResponseDto {
        TODO("Not yet implemented")
    }

    override fun getTodoList(): List<TodoResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getTodayTodoList(): List<TodoResponseDto> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto {
        TODO("Not yet implemented")
    }

    override fun getAllComments(todoId: Long): List<CommentResponseDto> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long) {
        TODO("Not yet implemented")
    }
}