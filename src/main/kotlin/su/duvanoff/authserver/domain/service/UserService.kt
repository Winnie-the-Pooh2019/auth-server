package su.duvanoff.authserver.domain.service

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import su.duvanoff.authserver.domain.model.AuthProvider
import su.duvanoff.authserver.domain.model.AuthorizedUser
import su.duvanoff.authserver.domain.model.User

interface UserService {

    fun save(userDto: OAuth2UserRequest, provider: AuthProvider): User

    fun saveAndMap(userDto: OAuth2UserRequest, provider: AuthProvider): AuthorizedUser
}