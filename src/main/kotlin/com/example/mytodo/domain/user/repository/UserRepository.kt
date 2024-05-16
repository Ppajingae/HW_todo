package com.example.mytodo.domain.user.repository

import com.example.mytodo.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}