package com.example.mytodo.domain.todo.controller

import com.example.mytodo.common.exception.StringLengthException
import com.example.mytodo.common.infra.security.jwt.JwtPlugin
import com.example.mytodo.domain.todo.dto.v1.*
import com.example.mytodo.domain.todo.service.v1.TodoService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockKExtension::class)
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin
): DescribeSpec({

    extensions(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val todoService = mockk<TodoService>()


    describe("/todo/{todoId} 로 GET 요청 으로 조회를 했을 경우"){
        context("id가 있을 경우"){
            it("Status 코드 200 과 조회 값을 Return 한다"){
                val todoId = 20L

                every {
                    todoService.getTodo(any())
                } returns TodoResponseDto(
                    id = todoId,
                    title = "Test",
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    content = "Test",
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                    createAt = LocalDateTime.now(),
                    updateAt = LocalDateTime.now(),
                    complete = false,
                    comment = mutableListOf(),
                )

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "NORMAL_MEMBER"
                )

                val result = mockMvc.perform(
                    get("/todo/$todoId")
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

                every { todoService.getTodoList() } returns listOf(TodoListResponseDto(
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
                ))

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    get("/todo/admin")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200
            }
        }

        context("관리자 권한이 없을 경우"){
            it("noAuthorityException 을 Return 한다"){
                every { todoService.getTodoList() } returns listOf(TodoListResponseDto(
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
                ))

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "NORMAL_MEMBER"
                )

                //shouldThrow 사용 하려고 했는데 해당 애러는 발생 하지 않았다고 나옵니다 이게 왜 그런 걸까요?
                mockMvc.perform(
                    get("/todo/admin")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isForbidden)

            }
        }
    }

    describe("/todo/today 로 GET 요청 으로 조회를 했을 경우"){
        context("오늘 마감일 경우"){
            it("오늘 마감인 데이터 만 불러 온다"){
//                val todoId = 20L
//
//                every {
//                    todoService.getTodayTodoList()
//                } returns listOf(
//                    TodoListResponseDto(
//                        id = todoId,
//                        title = "Test",
//                        todoType = TodoType.STUDY,
//                        importance = Importance.NORMAL,
//                        content = "Test",
//                        startTime = LocalDateTime.now(),
//                        endTime = LocalDateTime.now(),
//                        createAt = LocalDateTime.now(),
//                        updateAt = LocalDateTime.now(),
//                        complete = false,
//                    ), TodoListResponseDto(
//                        id = 21L,
//                        title = "Test",
//                        todoType = TodoType.STUDY,
//                        importance = Importance.NORMAL,
//                        content = "Test",
//                        startTime = LocalDateTime.now().minusDays(1),
//                        endTime = LocalDateTime.now().plusDays(1),
//                        createAt = LocalDateTime.now().minusDays(1),
//                        updateAt = LocalDateTime.now().minusDays(1),
//                        complete = false,
//                    ), TodoListResponseDto(
//                        id = 22L,
//                        title = "Test",
//                        todoType = TodoType.STUDY,
//                        importance = Importance.NORMAL,
//                        content = "Test",
//                        startTime = LocalDateTime.now().minusDays(1),
//                        endTime = LocalDateTime.now().plusDays(1),
//                        createAt = LocalDateTime.now().minusDays(1),
//                        updateAt = LocalDateTime.now().minusDays(1),
//                        complete = false,
//                    )
//                )
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    get("/todo/day")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//                val objectMapper = jacksonObjectMapper()
//                val responseBody: List<TodoListResponseDto> = objectMapper.readValue(result.response.contentAsString)
//
//                result.response.status shouldBe 200
//                responseBody.size shouldBe 1


            }
        }
    }

    describe("/todo 로 POST 요청 으로 작성을 했을 경우"){
        context("제목의 길이가 200자 이상 이거나 1자 이하일 경우"){
            it("StringLengthException 을 throw"){

                val todoCreateRequestDto = TodoCreateRequestDto(
                    title = "",
                    content = "test",
                    userId = 1L,
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                )

                every {
                    todoService.createTodo(todoCreateRequestDto)
                } throws StringLengthException("최소 1자 이상 200자 까지 입력 가능 합니다")


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    post("/todo")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("내용의 길이가 200자 이상 이거나 1자 이하일 경우"){
            it("StringLengthException 을 throw"){

                val todoCreateRequestDto = TodoCreateRequestDto(
                    title = "test",
                    content = "",
                    userId = 1L,
                    todoType = TodoType.STUDY,
                    importance = Importance.NORMAL,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now(),
                )

                every {
                    todoService.createTodo(todoCreateRequestDto)
                } throws StringLengthException("최소 1자 이상 1000자 까지 입력 가능 합니다")


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    post("/todo")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
//        context("정상적 으로 보낼 경우"){
//            it("Status 200을 리턴"){
//
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
//                every {
//                    todoService.createTodo(todoCreateRequestDto)
//                } returns TodoResponseDto(
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
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    post("/todo")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//
//                result.response.status shouldBe 200
//
//            }
//        }

    }

    describe("/todo/{todoId} 로 PUT 요청 으로 업데이트를 했을 경우"){
        context("제목의 길이가 200자 이상 이거나 1자 이하일 경우"){
            it("StringLengthException 을 throw"){
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

                every {
                    todoService.updateTodo(todoId, todoUpdateRequestDto)
                } throws StringLengthException("최소 1자 이상 200자 까지 입력 가능 합니다")


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    put("/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
        context("내용의 길이가 200자 이상 이거나 1자 이하일 경우"){
            it("StringLengthException 을 throw"){
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

                every {
                    todoService.updateTodo(todoId, todoCreateUpdateDto)
                } throws StringLengthException("최소 1자 이상 1000자 까지 입력 가능 합니다")


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    put("/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 400

            }
        }
//        context("정상적 으로 보낼 경우"){
//            it("Status 200을 리턴"){
//
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
//                every {
//                    todoService.updateTodo(todoId, todoUpdateRequestDto)
//                } returns TodoResponseDto(
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
//
//                val jwtToken = jwtPlugin.generateAccessToken(
//                    subject = "1",
//                    email = "yrjo@gmail.com",
//                    role = "ADMIN"
//                )
//
//                val result = mockMvc.perform(
//                    put("/todo/$todoId")
//                        .header("Authorization", "Bearer $jwtToken")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andReturn()
//
//
//                result.response.status shouldBe 200
//
//            }
//        }

    }

    describe("/todo/{todoId} 로 DELETE 요청 으로 삭제를 했을 경우"){
        context("정상적 으로 보낼 경우"){
            it("Status 200을 리턴"){

                val todoId = 20L


                every {
                    todoService.deleteTodo(todoId)
                }


                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "yrjo@gmail.com",
                    role = "ADMIN"
                )

                val result = mockMvc.perform(
                    delete("/todo/$todoId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()


                result.response.status shouldBe 204

            }
        }

    }

})