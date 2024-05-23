package com.example.mytodo.domain.todo.service

import com.example.mytodo.domain.common.exception.IdNotFoundException
import com.example.mytodo.domain.common.exception.NotCompleteException
import com.example.mytodo.domain.todo.dto.*
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.todo.entity.toListResponse
import com.example.mytodo.domain.todo.entity.toResponse
import com.example.mytodo.domain.todo.repository.TodoRepository
import com.example.mytodo.domain.user.service.CommonUserService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val userService: CommonUserService,
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
        userService.searchUserById(userId)

        return todoRepository.findByUserId(userId).map { it.toListResponse() }
    }




    @Transactional
    override fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto {
        val user = userService.searchUserById(todoCreateRequestDto.userId)

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

        val todo = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        todo.update(todoUpdateRequestDto)

        return todo.toResponse()

    }



    @Transactional
    override fun deleteTodo(todoId: Long) {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        if(!result.checkComplete()) throw NotCompleteException("완료 상태로 전환 후에 다시 시도 해주세요")

        todoRepository.deleteById(todoId)
    }

}
