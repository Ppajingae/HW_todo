package com.example.mytodo.domain.user.repository.v1

import com.example.mytodo.domain.user.entity.v1.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean
}