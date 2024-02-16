package id.alpha.features.login.login

import id.alpha.libraries.core.state.Intent

sealed class LoginIntent : Intent {
    data object UserLogin : LoginIntent()
    data class UpdateName(val name: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
}