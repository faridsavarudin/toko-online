package id.alpha.features.login.login

import id.alpha.apis.authentication.Login
import id.alpha.libraries.core.state.Async

data class LoginState (
    val asyncLogin: Async<Login> = Async.Default,
    val name: String = "",
    val password: String = ""
)