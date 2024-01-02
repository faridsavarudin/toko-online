import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import id.alpha.features.home.Home
import id.alpha.libraries.core.viewmodel.LocalViewModelHost
import id.alpha.libraries.core.viewmodel.ViewModelHost
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }

    CompositionLocalProvider(
        LocalViewModelHost provides viewModelHost
    ) {
        MaterialTheme {
            Home()
        }
    }}