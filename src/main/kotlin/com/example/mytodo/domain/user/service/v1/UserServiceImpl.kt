package com.example.mytodo.domain.user.service.v1

import com.example.mytodo.common.exception.IdNotFoundException
import com.example.mytodo.common.exception.ModelNotFoundException
import com.example.mytodo.common.exception.NotAuthenticationException
import com.example.mytodo.common.infra.security.PasswordEncoder
import com.example.mytodo.common.infra.security.jwt.JwtPlugin
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
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
): UserService {

    @Transactional
    override fun signUp(membershipRequestDto: MembershipRequestDto): UserResponseDto {
        if(userRepository.existsByEmail(membershipRequestDto.email)) throw IllegalStateException("중복 되는 이메일이 있습니다")
        if(userRepository.existsByNickname(membershipRequestDto.nickname)) throw IllegalStateException("중복 되는 닉네임이 있습니다")
        val encodedPassword = passwordEncoder.set().encode(membershipRequestDto.password)

        return userRepository.save(
            User(
                email = membershipRequestDto.email,
                nickname = membershipRequestDto.nickname,
                password = encodedPassword,
                isAdmin = when(membershipRequestDto.admin.name){
                    "ADMIN" -> Admin.ADMIN
                    "NORMAL_MEMBER" -> Admin.NORMAL_MEMBER
                    else -> throw IllegalArgumentException("다른 권한은 들어 갈 수 없습니다")
                }
            )
        ).toResponse()
    }

    @Transactional
    override fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val userResult = userRepository.findByEmail(loginRequestDto.email)?: throw ModelNotFoundException("존재 하지 않는 이메일 입니다")

        if(!passwordEncoder.set().matches(loginRequestDto.password, userResult.password)){
            throw NotAuthenticationException("비밀번호가 일치 하지 않습니다")
        }
        return LoginResponseDto(jwtPlugin.generateAccessToken(userResult.id.toString(),userResult.email, userResult.isAdmin.name))
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
    override fun deleteAdminUserProfile(userId: Long){
        val result = userRepository.findByIdOrNull(userId) ?: throw IdNotFoundException("User not found")
        userRepository.delete(result)
    }


    override fun getAdminUserProfileList(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toResponse() }
    }

}