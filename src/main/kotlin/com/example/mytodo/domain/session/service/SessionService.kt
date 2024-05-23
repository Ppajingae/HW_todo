package com.example.mytodo.domain.session.service

import com.example.mytodo.domain.common.exception.NoAuthorityException
import com.example.mytodo.domain.common.exception.NotSessionException
import com.example.mytodo.domain.common.exception.TimeOutException
import com.example.mytodo.domain.session.entity.Session
import com.example.mytodo.domain.session.repository.SessionRepository
import com.example.mytodo.domain.user.dto.LoginRequestDto
import com.example.mytodo.domain.user.service.CommonUserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val userService: CommonUserService
) {

    fun getSession(userId: Long){
        val result = sessionRepository.findByUserId(userId)?: throw NoAuthorityException("해당 이메일은 존재하지 않습니다")
        if(result.checkTimeOut()) throw TimeOutException("세션 만료")

    }

    fun getAdminSession(correctionId: Long){
        val result = sessionRepository.findByUserId(correctionId)?: throw NoAuthorityException("해당 이메일은 존재하지 않습니다")
        if(!result.checkAdmin()) throw NoAuthorityException("관리자 권한이 없습니다")
        if(result.checkTimeOut()) throw TimeOutException("세션 만료")

    }

    fun createSession(loginRequestDto: LoginRequestDto){
        val result = userService.searchUserByEmail(loginRequestDto.email, loginRequestDto.password)

        sessionRepository.save(
            Session(
                userId = result.id!!,
                email = result.email,
                isAdmin = result.isAdmin,
            )
        )
    }

    fun deleteSession(){

    }

    fun deleteSessionByUserId(userId: Long){
        val result = sessionRepository.findByUserId(userId)?: throw NotSessionException("204")
        sessionRepository.delete(result)
    }
}