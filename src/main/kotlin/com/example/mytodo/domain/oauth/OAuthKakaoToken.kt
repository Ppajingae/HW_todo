package com.example.mytodo.domain.oauth

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthKakaoToken(
    @JsonProperty("access_token")
    val accessToken: String?,
    @JsonProperty("token_type")
    val tokenType: String?,
    @JsonProperty("refresh_token")
    val refreshToken: String?,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,

    val scope: String?,
)