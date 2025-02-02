package su.duvanoff.authserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.anyRequest().authenticated()
        }

        return http
            .oauth2Login(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun users(): UserDetailsService {
        val user = User.builder()
            .username("admin")
            .password("{noop}password")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}