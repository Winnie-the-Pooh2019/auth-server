package su.duvanoff.authserver.domain.model

import java.time.LocalDate
import java.util.UUID

class LocalUser(
    val id : UUID? = null,
    val username: String,
    val email: String,
    val password: String,
    val enabled: Boolean,
    val firstName: String,
    val lastName: String,
    val patronymic : String,
    val birthDate: LocalDate,
    val avatarUrl: String
) {
}