package id.alpha.features.login.register

import id.alpha.libraries.core.state.Async

data class RegisterState (
    val asyncRegister: Async<String> = Async.Default,
    val name: String = "",
    val password: String = ""
)