package com.example.mytodo.common

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime


@MappedSuperclass
abstract class DateTime {

    private lateinit var createTime: LocalDateTime

    private lateinit var updateTime: LocalDateTime

    @PrePersist
    fun firstDateTime() {
        createTime = LocalDateTime.now()
        updateTime = createTime
    }

    @PreUpdate
    fun updateDateTime() {
        updateTime = LocalDateTime.now()
    }

    fun getCreatedTime(): LocalDateTime {
        return createTime
    }

    fun getUpdatedTime(): LocalDateTime {
        return updateTime
    }
}