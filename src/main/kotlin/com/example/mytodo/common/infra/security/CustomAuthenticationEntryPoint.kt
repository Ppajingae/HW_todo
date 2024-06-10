package com.example.mytodo.common.infra.security

import com.example.mytodo.common.exception.dto.ErrorResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        val objectMapper = ObjectMapper()

        val failed = objectMapper.writeValueAsString(ErrorResponseDto("유저 인증에 실패 했습니다 로그인을 해주세요"))

        response.writer.write(failed)
    }
}