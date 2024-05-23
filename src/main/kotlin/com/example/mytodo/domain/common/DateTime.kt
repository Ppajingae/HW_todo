package com.example.mytodo.domain.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
@MappedSuperclass
abstract class DateTime {

    @Column(name = "create_at")
    private var createAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_at")
    private var updateAt: LocalDateTime = LocalDateTime.now()

    @PrePersist
    fun firstDateTime() {
        createAt = LocalDateTime.now()
        updateAt = createAt
    }

    @PreUpdate
    fun setUpdateAt() {
        updateAt = LocalDateTime.now()
    }

    fun getCreateAt(): LocalDateTime {
        return createAt
    }

    fun getUpdateAt(): LocalDateTime {
        return updateAt
    }
}