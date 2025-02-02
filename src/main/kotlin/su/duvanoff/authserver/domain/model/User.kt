package su.duvanoff.authserver.domain.model

import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID? = null,
    val email: String,
    val isActive: Boolean,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val patronymic: String? = null,
    val birthDate: LocalDate? = null,
    val avatarUrl: String? = null,
) {
    companion object {
        fun builder(email: String, isActive: Boolean): UserBuilder = UserBuilder(email=email, isActive=isActive)

        fun from(user: User): UserBuilder {
            val userBuilder = UserBuilder(
                id = user.id,
                email = user.email,
                password = user.password,
                isActive = user.isActive,
                firstName = user.firstName,
                lastName = user.lastName,
                patronymic = user.patronymic,
                birthDate = user.birthDate,
                avatarUrl = user.avatarUrl
            )

            return userBuilder
        }

        class UserBuilder constructor(
            private var id: UUID? = null,
            private var email: String,
            private var isActive: Boolean,
            private var password: String? = null,
            private var firstName: String? = null,
            private var lastName: String? = null,
            private var patronymic: String? = null,
            private var birthDate: LocalDate? = null,
            private var avatarUrl: String? = null,
        ) {

            fun email(email: String): UserBuilder {
                this.email = email
                return this
            }

            fun password(password: String): UserBuilder {
                this.password = password
                return this
            }

            fun isActive(active: Boolean): UserBuilder {
                this.isActive = active
                return this
            }

            fun id(id: UUID): UserBuilder {
                this.id = id
                return this
            }

            fun firstName(firstName: String): UserBuilder {
                this.firstName = firstName
                return this
            }

            fun lastName(lastName: String): UserBuilder {
                this.lastName = lastName
                return this
            }

            fun patronymic(patronymic: String): UserBuilder {
                this.patronymic = patronymic
                return this
            }

            fun avatarUrl(avatarUrl: String): UserBuilder {
                this.avatarUrl = avatarUrl
                return this
            }

            fun birthDate(birthDate: LocalDate): UserBuilder {
                this.birthDate = birthDate
                return this
            }

            fun build(): User {
                return User(
                    id = this.id,
                    email = this.email,
                    password = this.password,
                    isActive = this.isActive,
                    firstName = this.firstName,
                    lastName = this.lastName,
                    patronymic = this.patronymic,
                    birthDate = this.birthDate,
                    avatarUrl = this.avatarUrl
                )
            }
        }
    }

    fun toAuthorizedUser(): AuthorizedUser =
        AuthorizedUser.Companion.AuthorizedUserBuilder(email, password, emptyList())
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .patronymic(patronymic)
            .birthDate(birthDate)
            .avatarUrl(avatarUrl)
            .build()
}