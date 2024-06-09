package com.example.mytodo.domain.user.controller.v1

import com.example.mytodo.domain.user.dto.v1.*
import com.example.mytodo.domain.user.service.v1.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto))
    }

    @PreAuthorize("hasRole('NORMAL_MEMBER') or hasRole('ADMIN')")
    @PutMapping("/{user_id}/profile")
    fun updateUserProfile(
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateRequestDto: MembershipUpdateRequestDto
    ): ResponseEntity<UserResponseDto> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId, membershipUpdateRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{user_id}/profile")
    fun updateAdminUserProfile(
        @PathVariable("user_id") userId: Long,
        @RequestBody membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto
    ): ResponseEntity<UserResponseDto> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateAdminUserProfile(userId, membershipUpdateAdminRequestDto))
    }

    @PreAuthorize("hasRole('NORMAL_MEMBER') or hasRole('ADMIN')")
    @PostMapping("/logout")
    fun logout(){
        TODO()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{user_id}/profile")
    fun deleteAdminUserProfile(
        @PathVariable("user_id") userId: Long,
    ): ResponseEntity<UserResponseDto> {
        userService.deleteAdminUserProfile(userId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/profile/all")
    fun getAdminUserProfileList(): ResponseEntity<List<UserResponseDto>> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAdminUserProfileList())
    }


}