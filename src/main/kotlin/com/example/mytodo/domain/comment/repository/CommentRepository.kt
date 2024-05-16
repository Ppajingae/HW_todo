package com.example.mytodo.domain.comment.repository

import com.example.mytodo.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>{


}