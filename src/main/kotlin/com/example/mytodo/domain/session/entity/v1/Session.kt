package com.example.mytodo.domain.session.entity.v1

import com.example.mytodo.domain.user.dto.v1.Admin
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "session")
class Session(

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "email")
    val email : String,

    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin")
    val isAdmin: Admin,

    @Column(name = "create_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_at")
    val updateAt: LocalDateTime = createAt.plusHours(6L),
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun checkAdmin():Boolean = isAdmin.name == Admin.ADMIN.name

    fun checkTimeOut():Boolean{
        println(updateAt)
        println(LocalDateTime.now())
        return updateAt < LocalDateTime.now()
    }
}