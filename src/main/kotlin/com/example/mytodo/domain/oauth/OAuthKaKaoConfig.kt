package com.example.mytodo.domain.oauth

import org.springframework.stereotype.Component

@Component
class OAuthKaKaoConfig {

    val clientId = "5875c8ced1c0e9f599acafb72b8234cc"

    val redirectUri = "http://localhost:8080/login/kakao/callback"
}