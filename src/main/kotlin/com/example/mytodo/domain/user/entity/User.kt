package com.example.mytodo.domain.user.entity

import com.example.mytodo.domain.comment.entity.Comment
import com.example.mytodo.domain.todo.entity.Todo
import com.example.mytodo.domain.user.dto.Admin
import com.example.mytodo.domain.user.dto.MembershipUpdateAdminRequestDto
import com.example.mytodo.domain.user.dto.MembershipUpdateRequestDto
import com.example.mytodo.domain.user.dto.UserResponseDto
import com.example.mytodo.domain.user.repository.UserRepository
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(

    @Column(name = "email", unique = true, nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin", nullable = false)
    var isAdmin: Admin,

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

    fun update(membershipUpdateRequestDto: MembershipUpdateRequestDto){
        email = membershipUpdateRequestDto.email
        password = membershipUpdateRequestDto.password
        nickname = membershipUpdateRequestDto.nickname
    }

    fun adminUpdate(membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto){
        isAdmin = membershipUpdateAdminRequestDto.isAdmin
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


