package com.example.mytodo.domain.common

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime


@MappedSuperclass
abstract class DateTime {

    private var createAt: LocalDateTime = LocalDateTime.now()

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