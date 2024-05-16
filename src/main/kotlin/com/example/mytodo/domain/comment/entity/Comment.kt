package com.example.mytodo.domain.comment.entity

import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.user.entity.User
import jakarta.persistence.*



@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "comment", nullable = false)
    val comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo

    ) : DateTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}