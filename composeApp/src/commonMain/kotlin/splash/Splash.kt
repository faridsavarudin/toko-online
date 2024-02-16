package splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.core.local.LocalValueDataSources
import id.alpha.libraries.core.viewmodel.rememberViewModel
import kotlinx.coroutines.delay

@Composable
fun Splash(
    navigateToHome: () -> Unit
) {
    val valueDataSources = LocalValueDataSources.current
    val viewModel = rememberViewModel { SplashViewModel(valueDataSources) }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        delay(3000)
        viewModel.sendIntent(SplashIntent.CheckToken)
    }

    if (state.isHasCheckToken) {
        if (state.isHasToken) {
            navigateToHome.invoke()
        } else {
            navigateToHome.invoke()
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadingScreen()
    }
}