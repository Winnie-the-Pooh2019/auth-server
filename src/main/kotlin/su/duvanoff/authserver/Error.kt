package su.duvanoff.authserver

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Error(
    val status: StatusCode,

    val message: String? = null
) {
    data class StatusCode(
        val code: Int,

        val name: String
    )
}
