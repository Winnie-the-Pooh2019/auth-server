package su.duvanoff.authserver.domain.repository

import su.duvanoff.authserver.domain.model.User
import java.util.*

interface UserRepository {

    fun save(user: User): User

    fun findById(id: UUID): Optional<User>

    fun findByEmail(email: String): Optional<User>
}