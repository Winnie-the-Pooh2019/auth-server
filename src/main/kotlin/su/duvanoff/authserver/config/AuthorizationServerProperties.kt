package su.duvanoff.authserver.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.authorizationserver")
class AuthorizationServerProperties {
    lateinit var issuerUrl: String
    lateinit var introspectionEndpoint: String
}