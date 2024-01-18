import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.features.home.Home
import id.alpha.features.productdetail.ProductDetail
import id.alpha.features.productlist.ProductList
import id.alpha.libraries.component.LocalImageResource
import id.alpha.libraries.component.utils.toData
import id.alpha.libraries.component.utils.toJson
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.LocalViewModelHost
import id.alpha.libraries.core.viewmodel.ViewModelHost
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }
    val productRepository = remember { ProductRepository(appConfigProvider) }
    val imageResourcesProvider = remember { ImageResourcesProvider() }


    CompositionLocalProvider(
        LocalViewModelHost provides viewModelHost,
        LocalAppConfig provides appConfigProvider,
        LocalProductRepository provides productRepository,
        LocalImageResource provides imageResourcesProvider
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
                        Home(
                            onClickItem = {
                                navigator.navigate("/detail/${it.name}")
                            },
                            onCategoryClick = {
                                val json = it.toJson()
                                println("dataaa $json")
                                navigator.navigate("/list/$json")
                            }
                        )
                    }
                    scene(
                        route = "/detail/{name}"
                    ) {
                        val productId = it.pathMap["id"].orEmpty().toIntOrNull() ?: 0
                        ProductDetail(productId)
                    }

                    scene(
                        route = "/list/{category}"
                    ) {
                        val dataJson = it.pathMap["category"] ?: "{}"
                        println("dataaaaa 2 -> $dataJson")
                        val data = dataJson.toData<CategoryItem>()
                        ProductList(data.name, data.id) {
                            navigator.popBackStack()
                        }
                    }
                }
            }
        }
    }
}