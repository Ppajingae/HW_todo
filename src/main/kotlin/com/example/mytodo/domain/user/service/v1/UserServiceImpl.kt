package com.example.mytodo.domain.user.service.v1

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.domain.user.dto.v1.*
import com.example.mytodo.domain.user.entity.v1.User
import com.example.mytodo.domain.user.entity.v1.toResponse
import com.example.mytodo.domain.user.repository.v1.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
): UserService {

    @Transactional
    override fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto {
        if(userRepository.existsByEmail(membershipRequestDto.email)) throw IllegalStateException("중복 되는 이메일이 있습니다")
        if(userRepository.existsByNickname(membershipRequestDto.nickname)) throw IllegalStateException("중복 되는 닉네임이 있습니다")

        return userRepository.save(
            User(
                email = membershipRequestDto.email,
                nickname = membershipRequestDto.nickname,
                password = membershipRequestDto.password,
                isAdmin = Admin.NORMAL_MEMBER,
            )
        ).toResponse()
    }

    @Transactional
    override fun login(loginRequestDto: LoginRequestDto) {
        TODO()
    }

    @Transactional
    override fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto): UserResponseDto {
        val result = userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("User not found")
        if(result.id != userId && userRepository.existsByEmail(memberUpdateRequestDto.email)) throw IllegalArgumentException("중복 되는 이메일이 있습니다")
        if(userRepository.existsByNickname(memberUpdateRequestDto.nickname)) throw IllegalArgumentException("중복 되는 닉네임이 있습니다")


        result.update(memberUpdateRequestDto)

        return userRepository.save(result).toResponse()
    }

    @Transactional
    override fun updateAdminUserProfile(
        correctionId: Long,
        userId: Long,
        membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto
    ): UserResponseDto {
          val result = userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("User not found")
          result.adminUpdate(membershipUpdateAdminRequestDto)
        return result.toResponse()
    }

    @Transactional
    override fun logout() {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteAdminUserProfile(correctionId: Long, userId: Long){
        val result = userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("User not found")
        userRepository.delete(result)
    }


    override fun getAdminUserProfileList(correctionId: Long): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponse() }
    }

}