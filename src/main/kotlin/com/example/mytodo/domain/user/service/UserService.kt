package com.example.mytodo.domain.user.service

import com.example.mytodo.domain.user.dto.*

interface UserService {

    fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto

    fun login(loginRequestDto: LoginRequestDto): UserResponseDto

    fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto): UserResponseDto

    fun updateAdminUserProfile(correctionId:Long, userId: Long, membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto): UserResponseDto

    fun logout()

    fun deleteAdminUserProfile(correctionId:Long, userId: Long): UserResponseDto

    fun getAdminUserProfileList(correctionId:Long): List<UserResponseDto>
}