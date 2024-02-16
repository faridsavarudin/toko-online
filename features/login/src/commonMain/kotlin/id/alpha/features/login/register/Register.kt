package id.alpha.features.login.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.alpha.apis.authentication.LocalAuthenticationRepository
import id.alpha.features.login.login.LoginIntent
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.core.state.Async
import id.alpha.libraries.core.viewmodel.rememberViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Register (
    navigateToLogin: () -> Unit
) {

    val authenticationRepository = LocalAuthenticationRepository.current
    val viewModel = rememberViewModel { RegisterViewModel(authenticationRepository) }
    val state by viewModel.uiState.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val async = state.asyncRegister) {
                is Async.Loading -> {
                    LoadingScreen()
                }
                is Async.Success -> {
                    coroutineScope.launch {
                        scaffoldState
                            .snackbarHostState
                            .showSnackbar(async.data)
                        delay(1000)
                        navigateToLogin.invoke()
                    }
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
                    .padding(horizontal = 24.dp)
            )

            Button(
                onClick = {
                    viewModel.sendIntent(LoginIntent.UserLogin)
                }, enabled = state.asyncRegister !is Async.Loading
            ) {
                Text("Login")
                
            }
        }
    }
}