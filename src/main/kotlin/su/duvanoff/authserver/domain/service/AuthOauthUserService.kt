package su.duvanoff.authserver.domain.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import su.duvanoff.authserver.domain.model.AuthProvider

@Service
class AuthOauthUserService(
    @Autowired
    private val userService: UserService
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oauth2User = super.loadUser(userRequest)

        val clientRegId = userRequest.clientRegistration.registrationId
        val provider = AuthProvider.findByName(clientRegId) ?: throw RuntimeException("No OAuth2 client registration found")

        return userService.saveAndMap(oauth2User, provider)
    }
}