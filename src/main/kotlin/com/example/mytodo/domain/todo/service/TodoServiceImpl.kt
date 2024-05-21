package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.exception.IdNotFoundException
import com.example.mytodo.domain.exception.NotCompleteException
import com.example.mytodo.domain.todo.dto.*
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.todo.entity.toListResponse
import com.example.mytodo.domain.todo.entity.toResponse
import com.example.mytodo.domain.todo.repository.TodoRepository
import com.example.mytodo.domain.user.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository,
): TodoService {

    override fun getTodo(todoId: Long): TodoResponseDto {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        return result.toResponse()
    }

    override fun getTodoList(): List<TodoListResponseDto> {

        return todoRepository.findAll().map { it.toListResponse() }
    }


    override fun getTodayTodoList(): List<TodoListResponseDto> {
        return todoRepository.getTodayTodoList().map { it.toListResponse() }
    }

    @Transactional
    override fun getTodoListSorted(sortRequestDto: SortRequestDto): List<TodoListResponseDto> {

         val sort = Sort.by(if(sortRequestDto.sortBy) Sort.Direction.ASC else Sort.Direction.DESC, sortRequestDto.columnName)

        return todoRepository.findAllBy(sort).map { it.toListResponse() }
    }

    override fun getUserTodoList(userId: Long): List<TodoListResponseDto> {
        userRepository.findByIdOrNull(userId)?: throw IdNotFoundException("존재하지 않는 유저 입니다")

        return todoRepository.findByUserId(userId).map { it.toListResponse() }
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

        if(!result.checkComplete()) throw NotCompleteException("완료 상태로 전환 후에 다시 시도 해주세요")

        todoRepository.deleteById(todoId)
    }

}
