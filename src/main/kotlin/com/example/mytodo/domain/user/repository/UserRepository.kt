package com.example.mytodo.domain.user.repository

import com.example.mytodo.domain.user.dto.UserResponseDto
import com.example.mytodo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<User, Long> {

//    @Query("SELECT u.isAdmin FROM User u WHERE u.id = :id")
//    fun findByIdAndIsAdmin(@Param("id")correctionId: Long):UserResponseDto

    fun findByEmail(email: String): User
}