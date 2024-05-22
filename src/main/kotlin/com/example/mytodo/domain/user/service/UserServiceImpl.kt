package com.example.mytodo.domain.user.service

import com.example.mytodo.domain.exception.IdNotFoundException
import com.example.mytodo.domain.exception.NoAuthorityException
import com.example.mytodo.domain.user.dto.*
import com.example.mytodo.domain.user.entity.User
import com.example.mytodo.domain.user.entity.toResponse
import com.example.mytodo.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
): UserService {

    @Transactional
    override fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto {
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
    override fun login(loginRequestDto: LoginRequestDto): UserResponseDto {

        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto): UserResponseDto {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun updateAdminUserProfile(
        correctionId: Long,
        userId: Long,
        membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto
    ): UserResponseDto {

//        val correction = userRepository.findByIdAndIsAdmin(correctionId)
//
//        if(correction.isAdmin != Admin.ADMIN) throw NoAuthorityException("권한이 없습니다")



        TODO("Not yet implemented")
    }

    @Transactional
    override fun logout() {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteAdminUserProfile(correctionId: Long, userId: Long): UserResponseDto {
//        val correction = userRepository.findByIdAndIsAdmin(correctionId)

//        if(correction.isAdmin != Admin.ADMIN) throw NoAuthorityException("권한이 없습니다")


        TODO("Not yet implemented")
    }

    override fun getAdminUserProfileList(correctionId: Long): List<UserResponseDto> {
//        val correction = userRepository.findByIdAndIsAdmin(correctionId)
//
//        if(correction.isAdmin != Admin.ADMIN) throw NoAuthorityException("권한이 없습니다")


        TODO("Not yet implemented")
    }

    fun searchUserById(userId: Long):User {
       return userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("아이디가 존재 하지 않습니다")
    }

    fun searchUserByEmail(email: String, password: String):User{

        val result = userRepository.findByEmail(email)

        if(!result.validPassword(password)) throw NoAuthorityException("비밀번호가 틀렸습니다")

        return userRepository.findByEmail(email)
    }


}