package com.example.mytodo.domain.user.controller

import com.example.mytodo.domain.user.dto.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
class UserController {

    @PostMapping("/signUp")
    fun signUp(@RequestBody membershipRequestDto: MembershipRequestDto): UserResponseDto {
        TODO()
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): UserResponseDto {
        TODO()
    }

    @PutMapping("/{user_id}/profile")
    fun updateUserProfile(
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateRequestDto: MembershipUpdateRequestDto): UserResponseDto {
        TODO()
    }

    @PutMapping("/{correction_id}/{user_id}/profile")
    fun updateAdminUserProfile(
        @PathVariable("correction_id") correctionId: Long,
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto
    ): UserResponseDto {
        TODO()
    }

    @PostMapping("/logout")
    fun logout(){
        TODO()
    }

    @DeleteMapping("/{correction_id}/{user_id}/profile")
    fun deleteAdminUserProfile(
        @PathVariable("correction_id") correctionId: Long,
        @PathVariable("user_id") userId: Long,
    ){
        TODO()
    }
}