package su.duvanoff.authserver.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import su.duvanoff.authserver.domain.repository.UserRepository

@Service
class AuthUserDetailsService (
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null)
            throw UsernameNotFoundException("User not found")

        return userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found") }
            .toAuthorizedUser()
    }
}