package com.example.mytodo.domain.comment.entity

import com.example.mytodo.domain.common.DateTime
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.user.dto.UserResponseDto
import com.example.mytodo.domain.user.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
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

fun Comment.toResponse():CommentResponseDto{
    return CommentResponseDto(
        id = id!!,
        comment = comment,
    )
}