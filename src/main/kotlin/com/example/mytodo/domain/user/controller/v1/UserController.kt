package com.example.mytodo.domain.user.controller.v1

import com.example.mytodo.domain.user.dto.v1.*
import com.example.mytodo.domain.user.service.v1.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signUp")
    fun signUp(@RequestBody membershipRequestDto: MembershipRequestDto): ResponseEntity<UserResponseDto> {
       return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(membershipRequestDto))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<Unit> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto))
    }

    @PutMapping("/{user_id}/profile")
    fun updateUserProfile(
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateRequestDto: MembershipUpdateRequestDto
    ): ResponseEntity<UserResponseDto> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId, membershipUpdateRequestDto))
    }

    @PutMapping("/{correction_id}/{user_id}/profile")
    fun updateAdminUserProfile(
        @PathVariable("correction_id") correctionId: Long,
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto
    ): ResponseEntity<UserResponseDto> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateAdminUserProfile(correctionId, userId, membershipUpdateAdminRequestDto))
    }

    @PostMapping("/logout")
    fun logout(){
        TODO()
    }

    @DeleteMapping("/{correction_id}/{user_id}/profile")
    fun deleteAdminUserProfile(
        @PathVariable("correction_id") correctionId: Long,
        @PathVariable("user_id") userId: Long,
    ): ResponseEntity<UserResponseDto> {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/{correction_id}/profile/all")
    fun getAdminUserProfileList(@PathVariable("correction_id") correctionId: Long): ResponseEntity<List<UserResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAdminUserProfileList(correctionId))
    }
}