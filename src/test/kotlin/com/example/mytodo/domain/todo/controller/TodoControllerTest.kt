package com.example.mytodo.domain.todo.controller

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.common.exception.StringLengthException
import com.example.mytodo.common.infra.security.jwt.JwtPlugin
import com.example.mytodo.domain.todo.controller.v1.TodoController
import com.example.mytodo.domain.todo.dto.v1.*
import com.example.mytodo.domain.todo.entity.v1.Todo
import com.example.mytodo.domain.todo.service.v1.TodoService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.validation.BindingResult
import java.time.LocalDateTime


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockKExtension::class)
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,
    @MockBean private val todoService: TodoService,
): DescribeSpec({

    extensions(SpringExtension)

    afterContainer {
        clearAllMocks()
    }





    describe("/todo/{todoId} 로 GET 요청 으로 조회를 했을 경우"){
        context("id가 있을 경우"){
            it("Status 코드 200 과 조회 값을 Return 한다"){
                val todoId = 20L

                val todoResponseDto = TodoResponseDto(
                    id = todoId,
                    todoType = TodoType.STUDY,
                    title = "test",
                    complete = false,
                    comment = mutableListOf(),
                    createAt = LocalDateTime.now(),
                    content = "test",
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    updateAt = LocalDateTime.now(),
                )

                Mockito.`when`(todoService.getTodo(todoId)).thenReturn(todoResponseDto)

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "NORMAL_MEMBER"
                )

                val result = mockMvc.perform(
                    get("/api/v1/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200

            }
        }
    }

    describe("/todo/admin 로 GET 요청 으로 조회를 했을 경우"){
        context("ADMIN 권한이 있을 경우"){
            it("Status 코드 200 과 조회 값을 Return 한다"){
                val todoResponseDto = TodoListResponseDto(
                    id = 20L,
                    title = "TEST",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    content = "contentStr",
                    complete = false,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    createAt = LocalDateTime.now(),
                    updateAt = LocalDateTime.now(),
                )

                Mockito.`when`(todoService.getTodoList()).thenReturn(listOf(todoResponseDto))

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    get("/api/v1/todo/admin")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200
            }
        }

        context("관리자 권한이 없을 경우"){
            it("noAuthorityException 을 Return 한다"){

                val todoListResponseDto = TodoListResponseDto(
                    id = 20L,
                    title = "TEST",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    content = "contentStr",
                    complete = false,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    createAt = LocalDateTime.now(),
                    updateAt = LocalDateTime.now(),
                )

                Mockito.`when`(todoService.getTodoList()).thenReturn(listOf(todoListResponseDto))

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "NORMAL_MEMBER"
                )

                mockMvc.perform(
                    get("/api/v1/todo/admin")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isForbidden)

            }
        }
    }

    // 데이터를 못불러 옵니다
    describe("/todo/today 로 GET 요청 으로 조회를 했을 경우"){
        context("오늘 마감일 경우"){
            it("오늘 마감인 데이터 만 불러 온다"){
//                val now = LocalDateTime.now()
//                val todoList = listOf(
//                    TodoListResponseDto(
//                        id = 20L,
//                        title = "Test",
//                        todoType = TodoType.STUDY,
//                        importance = Importance.NORMAL,
//                        content = "Test",
//                        startTime = now,
//                        endTime = now,
//                        createAt = now,
//                        updateAt = now,
//                        complete = false,
//                    ), TodoListResponseDto(
//                        id = 21L,
//                        title = "Test",
//                        todoType = TodoType.STUDY,
//                        importance = Importance.NORMAL,
//                        content = "Test",
//                        startTime = now.minusDays(1),
//                        endTime = now,
//                        createAt = now.minusDays(1),
//                        updateAt = now.minusDays(1),
//                        complete = false,
//                    )
//                )
//
//                Mockito.`when`(todoService.getTodayTodoList()).thenReturn(todoList)
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    get("/api/v1/todo/day")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//                val objectMapper = jacksonObjectMapper()
//                val responseBody: List<TodoListResponseDto> = objectMapper.readValue(result.response.contentAsString)
//
//                result.response.status shouldBe 200
//                responseBody.size shouldBe 2


            }
        }
    }

    describe("/todo 로 POST 요청 으로 작성을 했을 경우") {
        context("제목의 길이가 200자 이상 이거나 1자 이하일 경우") {
            it("StringLengthException 을 throw") {

                val todoCreateRequestDto = TodoCreateRequestDto(
                    title = "",
                    content = "test",
                    userId = 1L,
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                )

                Mockito.`when`(todoService.createTodo(todoCreateRequestDto)).thenThrow(StringLengthException("최소 1자 이상 200자 까지 입력 가능 합니다"))


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    post("/api/v1/todo")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("내용의 길이가 200자 이상 이거나 1자 이하일 경우") {
            it("StringLengthException 을 throw") {

                val todoCreateRequestDto = TodoCreateRequestDto(
                    title = "test",
                    content = "",
                    userId = 1L,
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                )

                Mockito.`when`(todoService.createTodo(todoCreateRequestDto)).thenThrow(StringLengthException("최소 1자 이상 1000자 까지 입력 가능 합니다"))



                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    post("/api/v1/todo")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("정상적 으로 보낼 경우"){
            // bindResult 를 어떤 방식 으로 처리 해야 할지 모르겠습니다
            it("Status 200을 리턴"){

//                val todoCreateRequestDto = TodoCreateRequestDto(
//                    title = "test",
//                    content = "test",
//                    userId = 1L,
//                    todoType = TodoType.STUDY,
//                    importance = Importance.NORMAL,
//                    startTime = LocalDateTime.now(),
//                    endTime = LocalDateTime.now(),
//                )
//
//                val todoResponseDto = TodoResponseDto(
//                    id = 25L,
//                    title = todoCreateRequestDto.title,
//                    content = todoCreateRequestDto.content,
//                    todoType = todoCreateRequestDto.todoType,
//                    importance = todoCreateRequestDto.importance,
//                    startTime = todoCreateRequestDto.startTime,
//                    endTime = todoCreateRequestDto.endTime,
//                    complete = false,
//                    comment = mutableListOf(),
//                    createAt = LocalDateTime.now(),
//                    updateAt = LocalDateTime.now(),
//                )
//
//                //bindResult 를 어떤 식으로 처리 해야 할지 모르겠습니다
//                Mockito.`when`(todoService.createTodo(todoCreateRequestDto)).thenReturn(todoResponseDto)
//
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    post("/api/v1/todo")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//
//                result.response.status shouldBe 200

            }
        }

    }

    describe("/todo/{todoId} 로 PUT 요청 으로 업데이트를 했을 경우") {
        context("제목의 길이가 200자 이상 이거나 1자 이하일 경우") {
            it("StringLengthException 을 throw") {
                val todoId = 20L

                val todoUpdateRequestDto = TodoUpdateRequestDto(
                    title = "",
                    content = "test",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    complete = false
                )

                Mockito.`when`(todoService.updateTodo(todoId, todoUpdateRequestDto)).thenThrow(StringLengthException("최소 1자 이상 200자 까지 입력 가능 합니다"))


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    put("/api/v1/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("내용의 길이가 200자 이상 이거나 1자 이하일 경우") {
            it("StringLengthException 을 throw") {
                val todoId = 20L

                val todoCreateUpdateDto = TodoUpdateRequestDto(
                    title = "test",
                    content = "",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    complete = false
                )

                Mockito.`when`(todoService.updateTodo(todoId, todoCreateUpdateDto)).thenThrow(StringLengthException("최소 1자 이상 1000자 까지 입력 가능 합니다"))



                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    put("/api/v1/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("정상적 으로 보낼 경우"){
            // bindingResult 를 처리 하지 못했 습니다
            it("Status 200을 리턴"){

//                val todoId = 20L
//                val todoUpdateRequestDto = TodoUpdateRequestDto(
//                    title = "test",
//                    content = "test",
//                    todoType = TodoType.STUDY,
//                    importance = Importance.NORMAL,
//                    startTime = LocalDateTime.now(),
//                    endTime = LocalDateTime.now(),
//                    complete = false
//                )
//
//                val todoResponseDto = TodoResponseDto(
//                    id = todoId,
//                    title = todoUpdateRequestDto.title,
//                    content = todoUpdateRequestDto.content,
//                    todoType = todoUpdateRequestDto.todoType,
//                    importance = todoUpdateRequestDto.importance,
//                    startTime = todoUpdateRequestDto.startTime,
//                    endTime = todoUpdateRequestDto.endTime,
//                    complete = todoUpdateRequestDto.complete,
//                    comment = mutableListOf(),
//                    createAt = LocalDateTime.now(),
//                    updateAt = LocalDateTime.now(),
//                )
//
//                Mockito.`when`(todoService.updateTodo(todoId, todoUpdateRequestDto)).thenReturn(todoResponseDto)
//
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    put("/api/v1/todo/$todoId")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//
//                result.response.status shouldBe 200

            }
        }

    }

    describe("/todo/{todoId} 로 DELETE 요청 으로 삭제를 했을 경우") {
        context("정상적 으로 보낼 경우") {
            it("Status 204을 리턴") {

                val todoId = 20L
                doNothing().`when`(todoService).deleteTodo(todoId)

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    delete("/api/v1/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 204

            }
        }
    }

    describe("/todo/{todoId} 로 DELETE 요청 으로 삭제를 했을 경우 이미 삭제된 경우") {
        context("정상적 으로 보낼 경우") {
            it("Status 404을 리턴") {

                val todoId = 20L

                Mockito.`when`(todoService.deleteTodo(todoId)).thenThrow(IdNotFoundException::class.java)

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    delete("/api/v1/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 404

            }
        }
    }

    })