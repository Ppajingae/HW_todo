package com.example.mytodo.domain.todo.service.v1

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.common.exception.NoAuthorityException
import com.example.mytodo.common.exception.NotCompleteException
import com.example.mytodo.domain.todo.dto.v1.*
import com.example.mytodo.domain.todo.entity.v1.Todo
import com.example.mytodo.domain.todo.entity.v1.toListResponse
import com.example.mytodo.domain.todo.entity.v1.toResponse
import com.example.mytodo.domain.todo.repository.v1.TodoRepository
import com.example.mytodo.domain.user.service.v1.CommonUserService
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

        return todoRepository.findByUserId(userId).map { it.toListResponse() }
    }




    @Transactional
    override fun createTodo(todoCreateRequestDto: TodoCreateRequestDto, userId: Long): TodoResponseDto {

        return todoRepository.save(
            Todo(
                title = todoCreateRequestDto.title,
                type =  todoCreateRequestDto.todoType,
                importance = todoCreateRequestDto.importance,
                startTime = todoCreateRequestDto.startTime ?: LocalDateTime.now(),
                endTime = todoCreateRequestDto.endTime,
                content = todoCreateRequestDto.content,
                user = userService.searchUserById(userId)
            )
        ).toResponse()
    }

    @Transactional
    override fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto, userId: Long): TodoResponseDto {

        val todo = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        if(!todo.checkUser(userId)) throw NoAuthorityException("작성자가 아닙니다")

        todo.update(todoUpdateRequestDto)

        return todo.toResponse()

    }



    @Transactional
    override fun deleteTodo(todoId: Long) {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        if(!result.checkComplete()) throw NotCompleteException("완료 상태로 전환 후에 다시 시도 해주세요")

        todoRepository.delete(result)
    }

}
