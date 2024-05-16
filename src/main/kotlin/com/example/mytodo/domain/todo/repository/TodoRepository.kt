package com.example.mytodo.domain.todo.repository

import com.example.mytodo.domain.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TodoRepository: JpaRepository<Todo, Long>{

    @Query("SELECT p FROM Todo p WHERE p.endTime = CURRENT_TIMESTAMP")
    fun getTodayTodoList(): List<Todo>
}