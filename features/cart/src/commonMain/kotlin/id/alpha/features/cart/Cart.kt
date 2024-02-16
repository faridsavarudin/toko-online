package id.alpha.features.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.model.cart.CartProductItem
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.core.network.UnauthorizedException
import id.alpha.libraries.core.state.Async
import id.alpha.libraries.core.viewmodel.rememberViewModel

@Composable
fun Cart(
    navigateToLogin: () -> Unit
) {
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { CartViewModel(productRepository) }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CartIntent.GetCart)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val cart = state.asyncCart) {
            is Async.Loading -> {
                item {
                    LoadingScreen()
                }
            }
            is Async.Success -> {
                val data = cart.data
                println("data -----> $data")
                if (data.isEmpty()) {
                    item {
                        Text("Tidak ada cart")
                    }
                } else {
                   items(data) {
                       CartItem(it)
                   }
                }
            }
            is Async.Failure -> {
                item {
                    val throwable = cart.throwable
                    if (throwable is UnauthorizedException) {
                        Button(
                            onClick = {
                                navigateToLogin.invoke()
                            }
                        ) {
                            Text("Login")
                        }
                    } else {
                        FailureScreen(cart.throwable.message.orEmpty())
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun CartItem(productItem: CartProductItem) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(all = 12.dp)) {
        Text(text = productItem.productId.toString())
    }
}