package com.example.mytodo.domain.oauth

import org.apache.juli.logging.LogFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange


@Controller
class OauthController(
    private val oAuthKaKaoConfig: OAuthKaKaoConfig
) {

    private val log = LogFactory.getLog(OauthController::class.java)

    @GetMapping("/login/kakao")
    fun getKakaoOAuth(): String{
        val url = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=${oAuthKaKaoConfig.clientId}" +
                "&redirect_uri=${oAuthKaKaoConfig.redirectUri}" +
                "&response_type=code"

        return "redirect:$url"
    }

    @GetMapping("/login/kakao/callback")
    @ResponseBody
    fun getKakaoAccessToken(code: String):String{
        val accessMap: MultiValueMap<String, String> = LinkedMultiValueMap()

        accessMap["grant_type"] = "authorization_code"
        accessMap["client_id"] = oAuthKaKaoConfig.clientId
        accessMap["redirect_uri"] = oAuthKaKaoConfig.redirectUri
        accessMap["code"] = code

        val httpRequestHeader = HttpHeaders()
        httpRequestHeader.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val kakaoHttpEntity = HttpEntity(accessMap, httpRequestHeader)

        val rt = RestTemplate()

        val accessToken = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoHttpEntity,
            OAuthKakaoToken::class.java
        )

       return accessToken.toString()
    }
}