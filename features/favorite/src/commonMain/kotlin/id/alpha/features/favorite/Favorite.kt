package id.alpha.features.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.component.AppTopBar
import id.alpha.libraries.component.product.ProductItemVerticalScreen
import id.alpha.libraries.core.viewmodel.rememberViewModel

@Composable
fun Favorite(
    onItemClick: (ProductItem) -> Unit
) {
    val repository = LocalProductRepository.current
    val viewModel = rememberViewModel { ProductFavoriteViewModel(repository) }
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ProductFavoriteIntent.GetFavorite)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Favorite"
            )
        }
    ) {
        LazyColumn {
            items(state.productList) {
                ProductItemVerticalScreen(
                    productItem = it,
                    onItemClick = onItemClick
                )
            }
            if (state.productList.isEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Product not found!")
                    }
                }
            }
        }
    }
}