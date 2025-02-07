package su.duvanoff.authserver.controller

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @GetMapping("/profile")
    fun profile(token: OAuth2AuthenticationToken) {
        println(token)
    }
}