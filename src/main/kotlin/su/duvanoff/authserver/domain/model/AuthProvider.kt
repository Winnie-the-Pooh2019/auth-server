package su.duvanoff.authserver.domain.model

enum class AuthProvider(val providerName: String) {
    GOOGLE("google"),
    GITHUB("github");

    companion object {
        fun findByName(name: String): AuthProvider? = entries.firstOrNull { it.providerName == name }
    }
}