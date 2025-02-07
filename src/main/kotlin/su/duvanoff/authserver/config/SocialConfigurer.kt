package su.duvanoff.authserver.config

import lombok.Setter
import lombok.experimental.Accessors
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler


@Setter
@Accessors(fluent = true, chain = true)
class SocialConfigurer : AbstractHttpConfigurer<SocialConfigurer, HttpSecurity>() {

    lateinit var oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>
    lateinit var oidcUserService: OAuth2UserService<OidcUserRequest, OAuth2User>

    lateinit var failureHandler: AuthenticationFailureHandler

    private val successHandler: AuthenticationSuccessHandler = SavedRequestAwareAuthenticationSuccessHandler()

    @Throws(Exception::class)
    override fun init(http: HttpSecurity) {
        http.oauth2Login { oauth2Login: OAuth2LoginConfigurer<HttpSecurity> ->
            if (this::oAuth2UserService.isInitialized) {
                oauth2Login.userInfoEndpoint().userService(this.oAuth2UserService)
            }

            oauth2Login.successHandler(this.successHandler)

            if (this::failureHandler.isInitialized) {
                oauth2Login.failureHandler(this.failureHandler)
            }
        }
    }
}