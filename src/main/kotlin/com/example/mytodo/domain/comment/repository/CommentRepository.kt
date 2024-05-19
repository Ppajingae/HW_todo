package com.example.mytodo.domain.comment.repository

import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository: JpaRepository<Comment, Long>{

    @Query("select c from Comment c where c.id = :todo_id")
    fun findAllById(@Param("todo_id")todoId: Long) : List<CommentResponseDto>
}