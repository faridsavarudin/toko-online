package id.alpha.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.ProductList
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.rememberViewModel
import kotlinx.coroutines.launch
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.state.Async

@Composable
fun Home(
    onClickItem: (ProductList) -> Unit
) {
    val appConfig = LocalAppConfig.current
    val productRepository = remember { ProductRepository(appConfig) }
    val homeViewModel = rememberViewModel { HomeViewModel(productRepository) }

    val homeState by homeViewModel.uiState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.sendIntent(
            HomeIntent.SetName("Utsman")
        )
        homeViewModel.sendIntent(
            HomeIntent.GetProductList
        )
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LazyColumn {
            item {
                Text(
                    text = homeState.name,
                    fontWeight = FontWeight.Bold
                )
            }

            when (val productList = homeState.asyncProductList) {
                is Async.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                is Async.Failure -> {
                    item {
                        Text(
                            text = productList.throwable.message.orEmpty(),
                            color = Color.Red
                        )
                    }
                }

                is Async.Success -> {
                    items(productList.data) {
                        ProductListItem(it) { product ->
                           /* homeViewModel.sendIntent(
                                HomeIntent.ShowSnackbar(
                                    name = product.name,
                                    snackbarState = scaffoldState.snackbarHostState,
                                    coroutineScope = scope
                                )
                            ) */
                            onClickItem.invoke(product)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun ProductListItem(productList: ProductList, onClickItem: (ProductList) -> Unit) {

    Column(
        modifier = Modifier.clickable {
            onClickItem.invoke(productList)
        }
    ) {
        Text(
            text = productList.name
        )
    }
}