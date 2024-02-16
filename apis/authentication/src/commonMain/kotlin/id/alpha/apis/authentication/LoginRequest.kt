package id.alpha.apis.authentication

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val name: String?,
    val password: String?
)