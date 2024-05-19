package com.example.mytodo.domain.todo.repository

import com.example.mytodo.domain.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: JpaRepository<Todo, Long> {

    @Query("SELECT p FROM Todo p WHERE p.endTime = CURRENT_DATE")
    fun getTodayTodoList(): List<Todo>

}