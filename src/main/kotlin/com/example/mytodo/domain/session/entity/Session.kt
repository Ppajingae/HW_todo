package com.example.mytodo.domain.session.entity

import com.example.mytodo.domain.common.DateTime
import com.example.mytodo.domain.user.dto.Admin
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "session")
class Session(

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "email")
    val email : String,

    @Column(name = "is_admin")
    val isAdmin: Admin,

):DateTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun checkAdmin():Boolean = isAdmin.name == Admin.ADMIN.name

    fun checkTimeOut():Boolean = getUpdateAt() < LocalDateTime.now()
}