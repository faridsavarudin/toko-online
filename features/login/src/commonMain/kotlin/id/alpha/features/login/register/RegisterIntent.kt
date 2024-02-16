package id.alpha.features.login.register

import id.alpha.libraries.core.state.Intent

sealed class RegisterIntent : Intent {
    data object UserRegister : RegisterIntent()
    data class UpdateName(val name: String) : RegisterIntent()
    data class UpdatePassword(val password: String) : RegisterIntent()
}