package com.example.mytodo.domain.todo.controller

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.common.infra.security.jwt.JwtPlugin
import com.example.mytodo.domain.todo.dto.v1.Importance
import com.example.mytodo.domain.todo.dto.v1.TodoResponseDto
import com.example.mytodo.domain.todo.dto.v1.TodoType
import com.example.mytodo.domain.todo.repository.v1.TodoRepository
import com.example.mytodo.domain.todo.service.v1.TodoService
import com.example.mytodo.domain.todo.service.v1.TodoServiceImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.Description
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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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


    describe("/{todoId} 로 GET 요청 으로 조회를 했을 경우"){
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

    describe(""){
        context(""){
            it(""){

            }
        }
    }
//
//    describe(""){
//        context(""){
//            it(""){
//
//            }
//        }
//    }
})