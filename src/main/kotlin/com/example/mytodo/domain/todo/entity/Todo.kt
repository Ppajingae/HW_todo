package com.example.mytodo.domain.todo.entity

import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.comment.entity.Comment
import com.example.mytodo.domain.todo.dto.Importance
import com.example.mytodo.domain.todo.dto.TodoResponseDto
import com.example.mytodo.domain.todo.dto.TodoType
import com.example.mytodo.domain.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "todo")
class Todo(

    @Column(name= "title", nullable = false)
    var title: String,

    @Enumerated(EnumType.STRING)
    @Column(name= "type", nullable = false)
    var type: TodoType,

    @Enumerated(EnumType.STRING)
    @Column(name= "importance", nullable = false)
    var importance: Importance,

    @Column(name= "content", nullable = true)
    var content : String?,

    @Column(name= "start_time", nullable = true)
    var startTime: LocalDateTime,

    @Column(name= "end_time", nullable = true)
    var endTime: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "todo")
    var comment: MutableList<Comment> = mutableListOf()

):DateTime(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}

fun Todo.toResponse(): TodoResponseDto {
    return TodoResponseDto(
        id = id!!,
        title = title,
        todoType = type,
        importance = importance,
        content = content,
        startTime = startTime,
        endTime = endTime,
        createAt = getCreatedTime(),
        updateAt = getUpdatedTime(),
    )
}