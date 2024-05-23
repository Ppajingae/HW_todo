package com.example.mytodo.domain.session.repository

import com.example.mytodo.domain.session.entity.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository: JpaRepository<Session, Long>{

    fun findByUserId(userId: Long): Session?
}