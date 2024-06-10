package com.example.mytodo.domain.user.entity.v1

import com.example.mytodo.domain.comment.entity.v1.Comment
import com.example.mytodo.domain.todo.entity.v1.Todo
import com.example.mytodo.domain.user.dto.v1.Admin
import com.example.mytodo.domain.user.dto.v1.MembershipUpdateAdminRequestDto
import com.example.mytodo.domain.user.dto.v1.MembershipUpdateRequestDto
import com.example.mytodo.domain.user.dto.v1.UserResponseDto
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

fun User.toResponse(): UserResponseDto {
    return UserResponseDto(
        id = id!!,
        email = email,
        nickname = nickname,
        isAdmin = Admin.NORMAL_MEMBER,
    )
}


