package com.example.mytodo.common.infra.security

import com.example.mytodo.common.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SpringSecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    fun filterChain(http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter):SecurityFilterChain{

        return http
            .httpBasic{ it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/login",
                    "/signUp",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                ).permitAll().anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling{
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .build()

    }
}