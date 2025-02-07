package su.duvanoff.authserver.domain.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import su.duvanoff.authserver.domain.model.AuthProvider
import su.duvanoff.authserver.domain.model.AuthorizedUser
import su.duvanoff.authserver.domain.model.User
import su.duvanoff.authserver.domain.repository.UserRepository
import su.duvanoff.authserver.exception.AuthorizationException

@Service
class BuildInUserService(
    @Autowired
    private val userRepository: UserRepository
) : UserService {

    override fun save(userDto: OAuth2UserRequest, provider: AuthProvider): User = when (provider) {
        AuthProvider.GITHUB -> this.saveUserFromGitHub(userDto)

        AuthProvider.GOOGLE -> this.saveUserFromGoogle(userDto as OidcUserRequest)
    }

    override fun saveAndMap(userDto: OAuth2UserRequest, provider: AuthProvider): AuthorizedUser =
        save(userDto, provider).toAuthorizedUser()

    fun saveUserFromGitHub(oauthUserRequest: OAuth2UserRequest): User {
        val userDto = DefaultOAuth2UserService().loadUser(oauthUserRequest)

        val email = userDto.getAttribute<String?>("email") ?: throw AuthorizationException("Email is required")

        val userOptional = userRepository.findByEmail(email)

        val userBuilder =
            if (userOptional.isEmpty)
                User.builder(email, true)
            else
                User.from(userOptional.get())

        if (userDto.getAttribute<String>("name") != null) {
            val splitted = userDto.getAttribute<String>("name")!!.split(" ")

            userBuilder.firstName(splitted[0])

            if (splitted.size > 1)
                userBuilder.lastName(splitted[1])

            if (splitted.size > 2)
                userBuilder.firstName(splitted[2])
        } else {
            userBuilder.firstName(userDto.getAttribute<String>("login")!!)
            userBuilder.lastName(userDto.getAttribute<String>("login")!!)
        }

        if (userDto.getAttribute<String>("avatar_url") != null)
            userBuilder.avatarUrl(userDto.getAttribute<String>("avatar_url")!!)

        return userRepository.save(userBuilder.build())
    }

    private fun saveUserFromGoogle(oidUserRequest: OidcUserRequest): User {
        val userDto = OidcUserService().loadUser(oidUserRequest)

        val email = userDto.getAttribute<String?>("email") ?: throw AuthorizationException("Email is required")

        val userOptional = userRepository.findByEmail(email)

        val userBuilder =
            if (userOptional.isEmpty)
                User.builder(email, true)
            else
                User.from(userOptional.get())

        if (userDto.getAttribute<String>("given_name") != null)
            userBuilder.firstName(userDto.getAttribute<String>("given_name")!!)

        if (userDto.getAttribute<String>("family_name") != null)
            userBuilder.lastName(userDto.getAttribute<String>("family_name")!!)

        if (userDto.getAttribute<String>("avatar_url") != null)
            userBuilder.avatarUrl(userDto.getAttribute<String>("avatar_url")!!)

        return userRepository.save(userBuilder.build())
    }
}