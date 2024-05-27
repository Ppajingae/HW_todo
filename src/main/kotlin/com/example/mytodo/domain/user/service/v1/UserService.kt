package com.example.mytodo.domain.user.service.v1

import com.example.mytodo.domain.user.dto.v1.*

interface UserService {

    fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto

    fun login(loginRequestDto: LoginRequestDto)

    fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto): UserResponseDto

    fun updateAdminUserProfile(correctionId:Long, userId: Long, membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto): UserResponseDto

    fun logout()

    fun deleteAdminUserProfile(correctionId:Long, userId: Long)

    fun getAdminUserProfileList(correctionId:Long): List<UserResponseDto>
}