package com.example.mytodo.domain.comment.service

import com.example.mytodo.domain.comment.dto.CommentCreateRequestDto
import com.example.mytodo.domain.comment.dto.CommentDeleteRequestDto
import com.example.mytodo.domain.comment.dto.CommentResponseDto
import com.example.mytodo.domain.comment.dto.CommentUpdateRequestDto
import com.example.mytodo.domain.comment.entity.Comment
import com.example.mytodo.domain.comment.entity.toResponse
import com.example.mytodo.domain.comment.repository.CommentRepository
import com.example.mytodo.domain.common.DeleteResponseDto
import com.example.mytodo.domain.exception.IdNotFoundException
import com.example.mytodo.domain.todo.repository.TodoRepository
import com.example.mytodo.domain.user.service.UserServiceImpl
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository,
    private val userService: UserServiceImpl,
):CommentService {

    @Transactional
    override fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto {
        val todoResult = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        userService.searchUserByEmail(commentCreateRequestDto.email, commentCreateRequestDto.password)

        return commentRepository.save(
            Comment(
                comment = commentCreateRequestDto.comment,
                todo = todoResult,
                user = todoResult.user,
            )
        ).toResponse()
    }

    override fun getAllComments(todoId: Long): List<CommentResponseDto> {
        val result = todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")

        return result.comment.map { it.toResponse() }
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto {
        todoRepository.findByIdOrNull(todoId)?: throw IdNotFoundException("Todo with ID $todoId not found")
        val commentResult = commentRepository.findByIdOrNull(commentId) ?: throw IdNotFoundException("Not Comment")
        userService.searchUserByEmail(commentUpdateRequestDto.email, commentUpdateRequestDto.password)



        val comment = commentUpdateRequestDto.comment

        commentResult.comment = comment

        return commentResult.toResponse()


    }

    @Transactional
    override fun deleteComment(todoId: Long, commentId: Long, commentDeleteRequestDto: CommentDeleteRequestDto):DeleteResponseDto {
        todoRepository.findByIdOrNull(todoId) ?: throw IdNotFoundException("Todo with ID $todoId not found")
        commentRepository.findByIdOrNull(commentId) ?: throw IdNotFoundException("Not Comment")
        userService.searchUserByEmail(commentDeleteRequestDto.email, commentDeleteRequestDto.password)

        commentRepository.deleteById(commentId)

        return DeleteResponseDto("삭제 완료!!")
    }


}