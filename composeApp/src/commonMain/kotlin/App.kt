import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import id.alpha.apis.authentication.AuthenticationRepository
import id.alpha.apis.authentication.LocalAuthenticationRepository
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.features.home.Home
import id.alpha.features.login.login.Login
import id.alpha.features.productdetail.ProductDetail
import id.alpha.features.productlist.ProductList
import id.alpha.libraries.component.LocalImageResource
import id.alpha.libraries.component.utils.toData
import id.alpha.libraries.component.utils.toJson
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.local.LocalValueDataSources
import id.alpha.libraries.core.local.ValueDataSources
import id.alpha.libraries.core.network.LocalLocalTokenDataSources
import id.alpha.libraries.core.network.LocalTokenDataSources
import id.alpha.libraries.core.viewmodel.LocalViewModelHost
import id.alpha.libraries.core.viewmodel.ViewModelHost
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import splash.Splash

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }
    val imageResourcesProvider = remember { ImageResourcesProvider() }
    val tabNavigator = remember { TabNavigator() }
    val valueDataSources = remember { ValueDataSources() }

    val localTokenDataSources = remember { LocalTokenDataSources(valueDataSources) }
    val productRepository = remember { ProductRepository(appConfigProvider, localTokenDataSources) }
    val authenticationRepository = remember { AuthenticationRepository(appConfigProvider, localTokenDataSources) }



    CompositionLocalProvider(
        LocalViewModelHost provides viewModelHost,
        LocalAppConfig provides appConfigProvider,
        LocalProductRepository provides productRepository,
        LocalImageResource provides imageResourcesProvider,
        LocalTabNavigator provides tabNavigator,
        LocalAuthenticationRepository provides authenticationRepository,
        LocalValueDataSources provides valueDataSources,
        LocalLocalTokenDataSources provides localTokenDataSources
    ) {
        MaterialTheme {
            PreComposeApp {
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = "/splash"
                ) {
                    scene(
                        route = "/splash"
                    ) {
                        Splash(
                            navigateToHome = {
                                navigator.navigate(
                                    route = "/home",
                                    options = NavOptions(
                                        launchSingleTop = true,
                                        popUpTo = PopUpTo.First(true)
                                    )
                                )
                            }
                        )
                    }

                    scene(
                        route = "/login"
                    ) {
                        Login {
                            navigator.navigate(
                                route = "/home",
                                options = NavOptions(
                                    launchSingleTop = true,
                                    popUpTo = PopUpTo.First(true)
                                )
                            )
                        }
                    }

                    scene(
                        route = "/register"
                    ) {
                        Register {
                            navigator.popBackStack()
                        }
                    }

                    scene(
                        route = "/home"
                    ) {
                        PagerScreen(navigator)
                    }
                    scene(
                        route = "/detail/{name}"
                    ) {
                        val productId = it.pathMap["id"].orEmpty().toIntOrNull() ?: 0
                        ProductDetail(
                            id = productId,
                            actionBack = {
                                navigator.popBackStack()
                            }
                        )
                    }

                    scene(
                        route = "/list/{category}"
                    ) {
                        val dataJson = it.pathMap["category"] ?: "{}"
                        println("dataaaaa 2 -> $dataJson")
                        val data = dataJson.toData<CategoryItem>()
                        ProductList(
                            data.name,
                            data.id,
                            {
                                navigator.navigate("/detail/${it.id}")
                            },
                            {
                                navigator.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}