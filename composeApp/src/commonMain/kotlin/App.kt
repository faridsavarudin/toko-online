import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import id.alpha.features.home.Home
import id.alpha.features.productdetail.ProductDetail
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.LocalViewModelHost
import id.alpha.libraries.core.viewmodel.ViewModelHost
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }

    CompositionLocalProvider(
        LocalViewModelHost provides viewModelHost,
        LocalAppConfig provides appConfigProvider
    ) {
        MaterialTheme {
            PreComposeApp {
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = "/home"
                ) {
                    scene(
                        route = "/home"
                    ) {
                        Home {
                            navigator.navigate("/detail/${it.name}")
                        }
                    }
                    scene(
                        route = "/detail/{name}"
                    ) {
                        val productId = it.pathMap["id"].orEmpty().toIntOrNull() ?: 0
                        ProductDetail(productId)
                    }
                }
            }
        }
    }
}