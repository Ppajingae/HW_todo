package com.example.mytodo.domain.todo.repository.v1

import com.example.mytodo.domain.todo.entity.v1.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Sort

@Repository
interface TodoRepository: JpaRepository<Todo, Long> {

    @Query("SELECT p FROM Todo p WHERE p.endTime = CURRENT_DATE ORDER BY p.complete ASC")
    fun getTodayTodoList(): List<Todo>

    fun findAllBy(sort: Sort): List<Todo>

    @Query("SELECT t FROM Todo t WHERE t.user.id = :userId")
    fun findByUserId(@Param("userId")userId: Long): List<Todo>


}