package com.example.mytodo.domain.user.entity

import com.example.mytodo.domain.comment.entity.Comment
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.user.dto.Admin
import com.example.mytodo.domain.user.dto.UserResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin", nullable = false)
    val isAdmin: Admin,

    @OneToMany(
        cascade = [(CascadeType.ALL)],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        mappedBy = "user")
    val todo : MutableList<Todo> = mutableListOf(),

    @OneToMany(
        cascade = [(CascadeType.ALL)],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        mappedBy = "user")
    val comment: MutableList<Comment> = mutableListOf(),


    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun validPassword(inputPassword: String):Boolean{
        return password == inputPassword
    }


}

fun User.toResponse():UserResponseDto{
    return UserResponseDto(
        id = id!!,
        email = email,
        nickname = nickname,
        isAdmin = Admin.NORMAL_MEMBER,
    )
}


