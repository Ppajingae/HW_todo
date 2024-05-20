package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.comment.repository.CommentRepository
import com.example.mytodo.domain.exception.IdNotFoundException
import com.example.mytodo.domain.exception.NotCompleteException
import com.example.mytodo.domain.todo.dto.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoUpdateRequestDto
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.todo.entity.toResponse
import com.example.mytodo.domain.todo.repository.TodoRepository
import com.example.mytodo.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
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
        val user = userRepository.findByIdOrNull(todoCreateRequestDto.userId)?: throw IdNotFoundException("User with ID ${todoCreateRequestDto.userId}")


        return todoRepository.save(
            Todo(
                title = todoCreateRequestDto.title,
                type =  todoCreateRequestDto.todoType,
                importance = todoCreateRequestDto.importance,
                startTime = todoCreateRequestDto.startTime ?: LocalDateTime.now(),
                endTime = todoCreateRequestDto.endTime,
                content = todoCreateRequestDto.content,
                user = user
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        val (title, todoType, importance, content, isComplete, startTime, endTime) = todoUpdateRequestDto

        result.title = title
        result.type = todoType
        result.importance = importance
        result.content = content
        result.isComplete = isComplete
        result.startTime = startTime ?: LocalDateTime.now()
        result.endTime = endTime

        return todoRepository.save(result).toResponse()

    }

    @Transactional
    override fun deleteTodo(todoId: Long) {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        if(!result.checkComplete()) throw NotCompleteException("완료 상태로 전환 후에 다시 시도해주세요")

        todoRepository.deleteById(todoId)
    }

}
