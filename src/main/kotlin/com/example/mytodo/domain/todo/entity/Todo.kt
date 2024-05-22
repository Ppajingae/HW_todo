package com.example.mytodo.domain.todo.entity

import com.example.mytodo.domain.comment.entity.Comment
import com.example.mytodo.domain.common.DateTime
import com.example.mytodo.domain.todo.dto.*
import com.example.mytodo.domain.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
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
    var comment: MutableList<Comment> = mutableListOf(),

    @ColumnDefault(value = "false")
    @Column(name="is_complete", nullable = false)
    var complete: Boolean = false

):DateTime(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun checkComplete() = complete

    fun update(todoUpdateRequestDto: TodoUpdateRequestDto) {
        title = todoUpdateRequestDto.title
        type = todoUpdateRequestDto.todoType
        importance = todoUpdateRequestDto.importance
        content = todoUpdateRequestDto.content
        startTime = todoUpdateRequestDto.startTime ?: LocalDateTime.now()
        endTime = todoUpdateRequestDto.endTime
    }


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
        createAt = getCreateAt(),
        updateAt = getUpdateAt(),
        comment = comment,
        complete = complete,
    )
}

fun Todo.toListResponse():TodoListResponseDto{
    return TodoListResponseDto(
        id = id!!,
        title= title,
        todoType = type,
        importance = importance,
        content = content,
        startTime = startTime,
        endTime = endTime,
        createAt = getCreateAt(),
        updateAt = getUpdateAt(),
        complete = complete
    )
}