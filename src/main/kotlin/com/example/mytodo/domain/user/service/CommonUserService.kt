package com.example.mytodo.domain.user.service

import com.example.mytodo.domain.common.exception.IdNotFoundException
import com.example.mytodo.domain.common.exception.NoAuthorityException
import com.example.mytodo.domain.user.entity.User
import com.example.mytodo.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommonUserService(
    val userRepository: UserRepository
) {

    fun searchUserById(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("아이디가 존재 하지 않습니다")
    }

    fun searchUserByEmail(email: String, password: String): User {

        val result = userRepository.findByEmail(email)?: throw IdNotFoundException("해당 유저의 아이디는 존재하지 않습니다")

        if(!result.validPassword(password)) throw NoAuthorityException("비밀번호가 틀렸습니다")

        return result
    }
}