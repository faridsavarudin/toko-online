package id.alpha.features.login.login

import id.alpha.apis.authentication.AuthenticationRepository
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<LoginState, LoginIntent>(LoginState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is LoginIntent.UserLogin -> {

            }

            is LoginIntent.UpdateName -> {
                updateName(intent.name)
            }

            is LoginIntent.UpdatePassword -> {
                updatePassword(intent.password)
            }
        }
    }

    private fun login() = viewModelScope.launch {
        val name = uiState.value.name
        val password = uiState.value.password
        authenticationRepository.login(name, password)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncLogin = it
                    )
                }
            }
    }

    private fun updateName(name: String) {
        updateUiState {
            copy(name = name)
        }
    }

    private fun updatePassword(password: String) {
        updateUiState {
            copy(password = password)
        }
    }
}