package su.duvanoff.authserver.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import su.duvanoff.authserver.domain.service.AuthOauthUserService
import su.duvanoff.authserver.domain.service.AuthUserDetailsService

@EnableWebSecurity
@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig(
    @Autowired
    private val customOAuth2UserService: AuthOauthUserService,

    @Autowired
    private val userDetailService: AuthUserDetailsService
) {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val socialConfigurer = SocialConfigurer().apply {
            this.oAuth2UserService = customOAuth2UserService
        }

        http.apply(socialConfigurer)

        http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService<UserDetailsService>(userDetailService)

        http.authorizeHttpRequests {
            it.anyRequest().authenticated()
        }

        return http
//            .oauth2Login(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .build()
    }
}