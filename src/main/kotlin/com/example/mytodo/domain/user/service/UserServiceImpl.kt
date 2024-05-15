package com.example.mytodo.domain.user.service

import com.example.mytodo.domain.user.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl: UserService {

    @Transactional
    override fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    @Transactional
    override fun logout() {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteAdminUserProfile(correctionId: Long, userId: Long): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun getAdminUserProfileList(correctionId: Long): List<UserResponseDto> {
        TODO("Not yet implemented")
    }
}