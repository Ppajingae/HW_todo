package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.comment.repository.CommentRepository
import com.example.mytodo.domain.todo.dto.*
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.todo.entity.toResponse
import com.example.mytodo.domain.todo.repository.TodoRepository
import com.example.mytodo.exception.IdNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,
): TodoService {

    override fun getTodo(todoId: Long): TodoResponseDto {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        return result.toResponse()
    }

    override fun getTodoList(): List<TodoResponseDto> {

        return todoRepository.findAll().map { it.toResponse() }
    }


    override fun getTodayTodoList(): List<TodoResponseDto> {

        return todoRepository.getTodayTodoList().map { it.toResponse() }
    }

    @Transactional
    override fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto {

        return todoRepository.save(
            Todo(
                title = todoCreateRequestDto.title,
                type =  todoCreateRequestDto.todoType,
                importance = todoCreateRequestDto.importance,
                content = todoCreateRequestDto.content,
                startTime = todoCreateRequestDto.startTime ?: LocalDateTime.now(),
                endTime = todoCreateRequestDto.endTime,
                user = todoCreateRequestDto.userId
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        val (title, todoType, importance, content, startTime, endTime) = todoUpdateRequestDto

        result.title = title
        result.type = todoType
        result.importance = importance
        result.content = content
        result.startTime = startTime ?: LocalDateTime.now()
        result.endTime = endTime

        return todoRepository.save(result).toResponse()
    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        todoRepository.delete(result)
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
