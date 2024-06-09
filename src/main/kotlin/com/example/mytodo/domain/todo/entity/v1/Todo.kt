package com.example.mytodo.domain.todo.entity.v1

import com.example.mytodo.domain.comment.entity.v1.Comment
import com.example.mytodo.common.DateTime
import com.example.mytodo.domain.todo.dto.v1.*
import com.example.mytodo.domain.user.entity.v1.User
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@SQLDelete(sql = "UPDATE todo SET deleted_at = current_timestamp,  is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
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
    @Column(name="complete", nullable = false)
    var complete: Boolean = false,



    ):DateTime(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name= "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @Column(name= "deleted_at", nullable = false)
    var deletedAt: LocalDateTime? = null

    fun checkComplete() = complete

    fun update(todoUpdateRequestDto: TodoUpdateRequestDto) {
        title = todoUpdateRequestDto.title
        type = todoUpdateRequestDto.todoType
        importance = todoUpdateRequestDto.importance
        content = todoUpdateRequestDto.content
        startTime = todoUpdateRequestDto.startTime ?: LocalDateTime.now()
        endTime = todoUpdateRequestDto.endTime
        complete = todoUpdateRequestDto.complete
    }


}

fun Todo.toResponse(): TodoResponseDto {
    val idValue = id ?: 0
    return TodoResponseDto(
        id = idValue,
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

fun Todo.toListResponse(): TodoListResponseDto {
    val idValue = id ?: 0
    return TodoListResponseDto(
        id = idValue,
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