import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import id.alpha.features.home.Home
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.LocalViewModelHost
import id.alpha.libraries.core.viewmodel.ViewModelHost

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }

    CompositionLocalProvider(
        LocalViewModelHost provides viewModelHost,
        LocalAppConfig provides appConfigProvider
    ) {
        MaterialTheme {
            Home()
        }
    }}