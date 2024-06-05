package com.example.mytodo.domain.comment.entity.v1

import com.example.mytodo.domain.comment.dto.v1.CommentResponseDto
import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.todo.entity.v1.Todo
import com.example.mytodo.domain.user.entity.v1.User
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*



@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "comment", nullable = false)
    var comment: String,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_id")
    val todo: Todo

    ) : DateTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toResponse(): CommentResponseDto {
    return CommentResponseDto(
        id = id!!,
        comment = comment,
        nickname = user.nickname
    )
}