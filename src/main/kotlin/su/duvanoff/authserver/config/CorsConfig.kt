package su.duvanoff.authserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Value("\${cors.origins}")
    lateinit var origins: String

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource();
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin(origins)
            addAllowedHeader(CorsConfiguration.ALL)
            addExposedHeader(CorsConfiguration.ALL)
            addAllowedMethod(CorsConfiguration.ALL)
        }

        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source);
    }
}