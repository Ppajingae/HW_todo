package com.example.mytodo.domain.session.service.v1

import com.example.mytodo.domain.common.exception.DuplicatedLoginException
import com.example.mytodo.domain.common.exception.NoAuthorityException
import com.example.mytodo.domain.common.exception.NotSessionException
import com.example.mytodo.domain.common.exception.TimeOutException
import com.example.mytodo.domain.session.entity.v1.Session
import com.example.mytodo.domain.session.repository.SessionRepository
import com.example.mytodo.domain.user.dto.v1.LoginRequestDto
import com.example.mytodo.domain.user.service.v1.CommonUserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val userService: CommonUserService
) {

    fun getSession(userId: Long){
        val result = sessionRepository.findByUserId(userId)?: throw NoAuthorityException("로그인을 해주세요")
        if(result.checkTimeOut()) {
            sessionRepository.deleteById(userId)
            throw TimeOutException("세션 만료")
        }

    }

    fun getAdminSession(correctionId: Long){
        val result = sessionRepository.findByUserId(correctionId)?: throw NoAuthorityException("로그인이 되어있지 않습니다 로그인을 해주세요")
        if(!result.checkAdmin()) throw NoAuthorityException("관리자 권한이 없습니다")
        if(result.checkTimeOut()){
            sessionRepository.deleteById(correctionId)
            throw TimeOutException("세션 만료")
        }
    }

    fun createSession(loginRequestDto: LoginRequestDto){
        val result = userService.searchUserByEmail(loginRequestDto.email, loginRequestDto.password)
        if(sessionRepository.existsByEmail(loginRequestDto.email)) throw DuplicatedLoginException("중복 로그인은 불가능 합니다")

        sessionRepository.save(
            Session(
                userId = result.id!!,
                email = result.email,
                isAdmin = result.isAdmin,
                createAt = LocalDateTime.now(),
                updateAt = LocalDateTime.now().plusHours(6L)
            )
        )
    }

    fun deleteSession(){
        TODO()
    }

    fun deleteSessionByUserId(userId: Long){
        val result = sessionRepository.findByUserId(userId)?: throw NotSessionException("204")
        sessionRepository.delete(result)
    }
}