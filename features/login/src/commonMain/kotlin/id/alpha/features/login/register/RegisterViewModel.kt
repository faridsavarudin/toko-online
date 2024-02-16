package id.alpha.features.login.register

import id.alpha.apis.authentication.AuthenticationRepository
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel<RegisterState, RegisterIntent>(RegisterState()) {

    override fun sendIntent(intent: Intent) {
        when(intent) {
            is RegisterIntent.UserRegister -> {
                register()
            }
            is RegisterIntent.UpdateName -> {
                updateName(intent.name)
            }

            is RegisterIntent.UpdatePassword -> {
                updatePassword(intent.password)
            }
        }
    }

    private fun updatePassword(password: String) {
        updateUiState {
            copy(password = password)
        }
    }

    private fun updateName(name: String) {
        updateUiState {
            copy(name = name)
        }
    }

    private fun register() = viewModelScope.launch {
        val name = uiState.value.name
        val password = uiState.value.password
        authenticationRepository.register(name, password)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncRegister = it)
                }
            }
    }
}