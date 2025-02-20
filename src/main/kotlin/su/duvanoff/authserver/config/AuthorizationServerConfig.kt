package su.duvanoff.authserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import su.duvanoff.authserver.domain.service.LocalOauthUserService
import java.time.Duration
import java.time.temporal.ChronoUnit


@Configuration(proxyBeanMethods = false)
class AuthorizationServerConfig(
    @Autowired
    private val authorizationServerProperties: AuthorizationServerProperties,

    @Autowired
    private val customOAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
) {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Throws(
        Exception::class
    )
    fun authServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

//        http
//            .oauth2Login {
//                it.userInfoEndpoint().userService(customOAuth2UserService)
//            }

        http.exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity?> ->
            exceptions.authenticationEntryPoint(
                LoginUrlAuthenticationEntryPoint("/login")
            )
        }

        return http.build()
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        return InMemoryRegisteredClientRepository(
            RegisteredClient.withId("test-client-id")
                .clientName("Test Client")
                .clientId("test-client")
                .clientSecret("{noop}test-client")
                .redirectUri("http://localhost:8080/code")
                .scope("read.scope")
                .scope("write.scope")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(
                    TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                        .refreshTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
                        .reuseRefreshTokens(false)
                        .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                        .build()
                )
                .build()
        )
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder()
            .issuer(authorizationServerProperties.issuerUrl)
            .tokenIntrospectionEndpoint(authorizationServerProperties.introspectionEndpoint)
            .build()
    }
}