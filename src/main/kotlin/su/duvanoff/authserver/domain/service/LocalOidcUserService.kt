package su.duvanoff.authserver.domain.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import su.duvanoff.authserver.domain.model.AuthProvider

@Service
class LocalOidcUserService(
    @Autowired
    private val userService: UserService
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val clientRegId = userRequest.clientRegistration.registrationId
        val provider = AuthProvider.findByName(clientRegId) ?: throw RuntimeException("client registration not found")

        return super.loadUser(userRequest)
    }
}