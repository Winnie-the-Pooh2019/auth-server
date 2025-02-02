package su.duvanoff.authserver.domain.repository.impl

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import su.duvanoff.authserver.domain.model.User
import su.duvanoff.authserver.domain.repository.UserRepository
import java.time.LocalDate
import java.util.*

@Repository
class BuiltInUserRepository : UserRepository, InitializingBean {

    private val _users: MutableMap<UUID, User> = HashMap()

    override fun save(user: User): User {
        val updatedUser = if (user.id == null) {
            User.from(user)
                .id(UUID.randomUUID())
                .build()
        } else user

        this._users[updatedUser.id!!] = updatedUser

        return updatedUser
    }

    override fun findById(id: UUID): Optional<User> = Optional.ofNullable(this._users[id])

    override fun findByEmail(email: String): Optional<User> =
        Optional.ofNullable(this._users.values.firstOrNull { it.email == email })

    override fun afterPropertiesSet() {
        this.save(User(
            email = "ivan@duvanoff.su",
            password = "{noop}password",
            isActive = true,
            firstName = "ivan",
            lastName = "ivan",
            birthDate = LocalDate.now(),
        ))
    }
}