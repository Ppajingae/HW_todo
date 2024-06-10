package com.example.mytodo.domain.todo.service

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.common.exception.NotCompleteException
import com.example.mytodo.domain.todo.dto.v1.Importance
import com.example.mytodo.domain.todo.dto.v1.TodoCreateRequestDto
import com.example.mytodo.domain.todo.dto.v1.TodoType
import com.example.mytodo.domain.todo.dto.v1.TodoUpdateRequestDto
import com.example.mytodo.domain.todo.entity.v1.Todo
import com.example.mytodo.domain.todo.repository.v1.TodoRepository
import com.example.mytodo.domain.todo.service.v1.TodoServiceImpl
import com.example.mytodo.domain.user.dto.v1.Admin
import com.example.mytodo.domain.user.entity.v1.User
import com.example.mytodo.domain.user.repository.v1.UserRepository
import com.example.mytodo.domain.user.service.v1.CommonUserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime


@ExtendWith(MockKExtension::class)
class TodoServiceTest:BehaviorSpec({

    extensions(SpringExtension)

    afterTest {
        clearAllMocks()
    }


    val todoRepository = mockk<TodoRepository>()
    val userService = mockk<CommonUserService>()
    val todoService = TodoServiceImpl(todoRepository, userService)
    val userRepository = mockk<UserRepository>()

    @Test
    fun `특정 TODO를 조회 해서 TODO가 있을 경우 TodoResponseDto 를 반환`(){
        val todoId = 20L
        every { todoRepository.findByIdOrNull(any()) } returns Todo(
            title = "et",
            comment = mutableListOf(),
            complete = false,
            content = "dfs",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            importance = Importance.NORMAL,
            type = TodoType.STUDY,
            user = User(
                email = "test@test",
                password = "test",
                todo = mutableListOf(),
                comment = mutableListOf(),
                isAdmin = Admin.ADMIN,
                nickname = "Kim"
            ))
    }

    @Test
    fun `특정 TODO를 조회 해서 TODO가 없을 경우 IdNotFoundException 을 Return`(){
        val todoId = 10L

        every { todoRepository.findByIdOrNull(any()) } returns null

        shouldThrow<IdNotFoundException> {
            todoService.getTodo(todoId)
        }
    }


    Given("모든 TODO 리스트를 전체 조회 해서"){
        When("TODO 리스트를 가져오면"){
            Then("List 로 반환"){

                val todo1 = Todo(
                    title = "Test TODO 1",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 1",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now().plusDays(1),
                    importance = Importance.NORMAL,
                    type = TodoType.WORK,
                    user = User(
                        email = "user1@test.com",
                        password = "password1",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.ADMIN,
                        nickname = "User1"
                    )
                )
                val todo2 = Todo(
                    title = "Test TODO 2",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 2",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now().plusDays(2),
                    importance = Importance.NORMAL,
                    type = TodoType.STUDY,
                    user = User(
                        email = "user2@test.com",
                        password = "password2",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.NORMAL_MEMBER,
                        nickname = "User2"
                    ))

                every { todoRepository.save(any()) } answers {
                    val savedTodo = firstArg<Todo>()
                    savedTodo.id = 1L
                    savedTodo
                }

                every { todoRepository.findAll() } returns listOf(todo1, todo2)

                val todoList = todoService.getTodoList()

                todoList.size shouldBe 2
                todoList[0].title shouldBe "Test TODO 1"
                todoList[1].title shouldBe "Test TODO 2"

            }
        }
    }

    Given("종료 시간이 오늘인 TODO 리스트를 전체 조회 해서"){
        When("TODO 리스트를 가져오면"){
            Then("List 로 반환"){

                val todo1 = Todo(
                    title = "Test TODO 1",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 1",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now().plusDays(1),
                    importance = Importance.NORMAL,
                    type = TodoType.WORK,
                    user = User(
                        email = "user1@test.com",
                        password = "password1",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.ADMIN,
                        nickname = "User1"
                    )
                )
                val todo2 = Todo(
                    title = "Test TODO 2",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 2",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    importance = Importance.NORMAL,
                    type = TodoType.STUDY,
                    user = User(
                        email = "user2@test.com",
                        password = "password2",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.NORMAL_MEMBER,
                        nickname = "User2"
                    ))

                every { todoRepository.save(todo1) } returns todo1
                every { todoRepository.save(todo2) } returns todo2

                every { todoRepository.getTodayTodoList() } returns listOf(todo2)

                val todoList = todoService.getTodayTodoList()

                todoList.size shouldBe 1
                todoList[0].title shouldBe "Test TODO 2"

            }
        }
    }

    Given("특정 유저의 TODO 리스트를 전체 조회 해서"){
        When("특정 유저의 id가 존재하지 않을 경우"){
            Then("IdNotFoundException 을 Throw"){
                val userId = 1L

                every { userService.searchUserById(userId) } throws IdNotFoundException("아이디가 존재 하지 않습니다")

                shouldThrow<IdNotFoundException> {
                    todoService.getUserTodoList(userId)
                }
            }
        }
        When("특정 유저의 TODO 리스트를 가져 오면"){
            Then("List 로 반환"){
                val userId = 5L
                val todo1 = Todo(
                    title = "Test TODO 1",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 1",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now().plusDays(1),
                    importance = Importance.NORMAL,
                    type = TodoType.WORK,
                    user = User(
                        email = "user1@test.com",
                        password = "password1",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.ADMIN,
                        nickname = "User1"
                    )
                )
                val todo2 = Todo(
                    title = "Test TODO 2",
                    comment = mutableListOf(),
                    complete = false,
                    content = "Content 2",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    importance = Importance.NORMAL,
                    type = TodoType.STUDY,
                    user = User(
                        email = "user2@test.com",
                        password = "password2",
                        todo = mutableListOf(),
                        comment = mutableListOf(),
                        isAdmin = Admin.NORMAL_MEMBER,
                        nickname = "User2"
                    ))


                every { userService.searchUserById(userId) } returns User(
                    email = "test",
                    password = "test",
                    nickname = "etst",
                    comment = mutableListOf(),
                    todo = mutableListOf(),
                    isAdmin = Admin.ADMIN,
                )
                every { todoRepository.save(todo1) } answers {
                    todo1.id = userId
                    todo1
                }
                every { todoRepository.save(todo2) } returns todo2

                every { todoRepository.findByUserId(userId) } returns listOf(todo1)

                val todoList = todoService.getUserTodoList(userId)

                todoList.size shouldBe 1
                todoList[0].title shouldBe "Test TODO 1"

            }
        }
    }

    Given("TODO 를 생성 해서"){
        When("todo 를 저장 하고"){
            Then("TodoResponseDto 를 반환"){

                val now = LocalDateTime.now()

                val user = User(
                    email = "test@email.com",
                    password = "test password",
                    nickname = "test nicname",
                    isAdmin = Admin.NORMAL_MEMBER,
                )
                val todo = Todo(
                    title = "test title 1",
                    type = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now.plusDays(1),
                    content = "Content 1",
                    comment = mutableListOf(),
                    complete = false,
                    user = user
                )

                val todoCreateRequestDto = TodoCreateRequestDto(
                    title = "test title 1",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now.plusDays(1),
                    content = "Content 1",
                    userId = 1L
                )

                every { userRepository.save(user) } answers {
                    user.id = 1L
                    user
                }

                every { userService.searchUserById(1L) } returns user

                every { todoRepository.save(any<Todo>()) } answers {
                    val savedTodo = firstArg<Todo>()
                    savedTodo.id = 1L
                    savedTodo
                }

                val createTodo = todoService.createTodo(todoCreateRequestDto)

                createTodo.title shouldBe "test title 1"

                createTodo.todoType shouldBe TodoType.STUDY

                createTodo.importance shouldBe Importance.NORMAL

                createTodo.startTime shouldBe now

                createTodo.content shouldBe "Content 1"

                createTodo.endTime shouldBe now.plusDays(1)

            }
        }
    }

    Given("특정 유저가 특정 TODO 를 업데이트 할 경우"){
        When("원하는 todo의 id가 존재하지 않을 경우"){
            Then("IdNotFoundException 을 Throw"){
                val todoId = 1L

                val todoUpdateRequestDto = TodoUpdateRequestDto(
                    title = "test title 1",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    content = "Content 1",
                    complete = true
                )

                every { todoRepository.findByIdOrNull(todoId) } throws IdNotFoundException("Todo with ID $todoId not found")

                shouldThrow<IdNotFoundException> {
                    todoService.updateTodo(todoId, todoUpdateRequestDto)
                }
            }
        }
        When("원하는 todo를 불러서 업데이트를 한 후에"){
            Then("TodoResponseDto 를 반환"){
                val todoId = 1L

                val now = LocalDateTime.now()

                val user = User(
                    email = "test@email.com",
                    password = "test password",
                    nickname = "test nicname",
                    isAdmin = Admin.NORMAL_MEMBER,
                )
                val todo = Todo(
                    title = "test title 1",
                    type = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now.plusDays(1),
                    content = "Content 1",
                    comment = mutableListOf(),
                    complete = false,
                    user = user
                )

                val todoUpdateRequestDto = TodoUpdateRequestDto(
                    title = "test title 1",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now,
                    content = "Content 1",
                    complete = true
                )

                every { userRepository.save(user) } answers {
                    user.id = 1L
                    user
                }

                every { userService.searchUserById(1L) } returns user

                every { todoRepository.findByIdOrNull(1L) } returns todo

                every { todoRepository.save(any<Todo>()) } answers {
                    todo.id = 1L
                    todo
                }

                val updateTodo = todoService.updateTodo(todoId, todoUpdateRequestDto)

                updateTodo.title shouldBe "test title 1"

                updateTodo.todoType shouldBe TodoType.STUDY

                updateTodo.importance shouldBe Importance.NORMAL

                updateTodo.startTime shouldBe now

                updateTodo.content shouldBe "Content 1"

                updateTodo.endTime shouldBe now

                updateTodo.complete shouldBe true

            }
        }
    }

    Given("특정 유저가 특정 TODO 를 삭제 할 경우"){
        When("원하는 유저의 todoId가 존재하지 않을 경우"){
            Then("IdNotFoundException 을 Throw"){
                val todoId = 1L

                every { todoRepository.findByIdOrNull(todoId) } throws IdNotFoundException("Todo with ID $todoId not found")

                shouldThrow<IdNotFoundException> {
                    todoService.deleteTodo(todoId)
                }
            }
        }
        When("complete가 false인 경우"){
            Then("NotCompleteException 을 Throw"){
                val todoId = 1L

                val now = LocalDateTime.now()

                val user = User(
                    email = "test@email.com",
                    password = "test password",
                    nickname = "test nicname",
                    isAdmin = Admin.NORMAL_MEMBER,
                )

                val todo = Todo(
                    title = "test title 1",
                    type = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now.plusDays(1),
                    content = "Content 1",
                    comment = mutableListOf(),
                    complete = false,
                    user = user
                )

                every { userRepository.save(user) } answers {
                    user.id = 1L
                    user
                }

                every { userService.searchUserById(1L) } returns user

                every { todoRepository.findByIdOrNull(todoId) } returns todo

                every { todoRepository.delete(any<Todo>()) } throws NotCompleteException("완료 상태로 전환 후에 다시 시도 해주세요")

                shouldThrow<NotCompleteException> {
                    todoService.deleteTodo(todoId)
                }
            }
        }
        When("원하는 todo 를 불러서 삭제"){
            Then("isDeleted 가 true 인지 확인"){
                val todoId = 1L

                val now = LocalDateTime.now()

                val user = User(
                    email = "test@email.com",
                    password = "test password",
                    nickname = "test nicname",
                    isAdmin = Admin.NORMAL_MEMBER,
                )

                val todo = Todo(
                    title = "test title 1",
                    type = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = now,
                    endTime = now.plusDays(1),
                    content = "Content 1",
                    comment = mutableListOf(),
                    complete = true,
                    user = user
                )


                every { userRepository.save(user) } answers {
                    user.id = 1L
                    user
                }

                every { userService.searchUserById(1L) } returns user

                every { todoRepository.findByIdOrNull(todoId) } returns todo

                every { todoRepository.delete(any<Todo>()) } returns Unit

                val result = todoService.deleteTodo(todoId)

                result shouldBe Unit
            }
        }
    }

})
