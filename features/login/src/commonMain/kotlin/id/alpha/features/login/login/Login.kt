package id.alpha.features.login.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.alpha.apis.authentication.LocalAuthenticationRepository
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.core.state.Async
import id.alpha.libraries.core.viewmodel.rememberViewModel

@Composable
fun Login(
    navigateToHome: () -> Unit
) {
    val authenticationRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { LoginViewModel(authenticationRepository) }
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        println("state ---> ${state.asyncLogin}")

        when (val async = state.asyncLogin) {
            is Async.Loading -> {
                LoadingScreen()
            }

            is Async.Success -> {
                navigateToHome.invoke()
            }

            is Async.Failure -> {
                FailureScreen(
                    message = async.throwable.message.orEmpty()
                )
            }

            else -> {}
        }

        OutlinedTextField(
            value = state.name,
            onValueChange = {
                viewModel.sendIntent(LoginIntent.UpdateName(it))
            },
            placeholder = {
                Text("Name")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.sendIntent(LoginIntent.UpdatePassword(it))
            },
            placeholder = {
                Text("Password")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
                .padding(
                    horizontal = 24.dp
                )
        )

        Button(
            onClick = {
                viewModel.sendIntent(LoginIntent.UserLogin)
            },
            enabled = state.asyncLogin !is Async.Loading
        ) {
            Text("Login")
        }
    }
}