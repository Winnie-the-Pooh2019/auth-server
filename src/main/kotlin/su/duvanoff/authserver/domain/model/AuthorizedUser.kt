package su.duvanoff.authserver.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.time.LocalDate
import java.util.*

class AuthorizedUser : SpringUser, OAuth2User {

    var id: UUID? = null
    var firstName: String? = null
    var lastName: String? = null
    var patronymic: String? = null
    var birthDate: LocalDate? = null
    var avatarUrl: String? = null

    var oauthAttributes: Map<String, Any> = HashMap()

    constructor (
        email: String,
        password: String,
        authorities: Collection<GrantedAuthority>
    ) : super(email, password, authorities)

    constructor(
        email: String,
        password: String,
        enabled: Boolean,
        accountNonExpired: Boolean,
        credentialsNonExpired: Boolean,
        accountNonLocked: Boolean,
        authorities: Collection<GrantedAuthority>,
    ) : super(
        email,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities
    )

    override fun getName(): String = username

    override fun getAttributes(): Map<String, Any> = oauthAttributes

    companion object {
        class AuthorizedUserBuilder {
            private val _authorizedUser: AuthorizedUser

            constructor(email: String, password: String?, authorities: Collection<GrantedAuthority> = emptyList()) {
                _authorizedUser = AuthorizedUser(email, password ?: "", authorities)
            }

            constructor(
                email: String,
                password: String?,
                enabled: Boolean = true,
                accountNonExpired: Boolean = true,
                credentialsNonExpired: Boolean = true,
                accountNonLocked: Boolean = true,
                authorities: Collection<GrantedAuthority> = emptyList(),
            ) {
                _authorizedUser = AuthorizedUser(
                    email,
                    password ?: "",
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    authorities
                )
            }

            fun id(id: UUID?): AuthorizedUserBuilder {
                _authorizedUser.id = id
                return this
            }

            fun firstName(firstName: String?): AuthorizedUserBuilder {
                _authorizedUser.firstName = firstName
                return this
            }

            fun lastName(lastName: String?): AuthorizedUserBuilder {
                _authorizedUser.lastName = lastName
                return this
            }

            fun patronymic(patronymic: String?): AuthorizedUserBuilder {
                _authorizedUser.patronymic = patronymic
                return this
            }

            fun birthDate(birthDate: LocalDate?): AuthorizedUserBuilder {
                _authorizedUser.birthDate = birthDate
                return this
            }

            fun avatarUrl(avatarUrl: String?): AuthorizedUserBuilder {
                _authorizedUser.avatarUrl = avatarUrl
                return this
            }

            fun oauthAttributes(oauthAttributes: Map<String, Any>): AuthorizedUserBuilder {
                _authorizedUser.oauthAttributes = oauthAttributes
                return this
            }

            fun build(): AuthorizedUser = _authorizedUser
        }
    }
}