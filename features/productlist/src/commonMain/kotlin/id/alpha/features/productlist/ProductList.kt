package id.alpha.features.productlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.component.product.ProductItemGridScreen
import id.alpha.libraries.component.product.ProductItemVerticalScreen
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.rememberViewModel


@Composable
fun ProductList(categoryName: String, categoryId: Int) {

    val productRepository = LocalProductRepository.current
    val appConfig = LocalAppConfig.current
    val viewModel = rememberViewModel { ProductListViewModel(productRepository, appConfig) }
    val state by viewModel.uiState.collectAsState()

    val pagingProduct = state.pagingData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ProductListIntent.GetProductList(categoryId))
        viewModel.sendIntent(ProductListIntent.SetCategoryName(categoryName))
    }

    LazyColumn {
        items(pagingProduct.itemCount) { index ->
            val item = pagingProduct[index]
            if (item != null) {
                ProductItemGridScreen(
                    productItem = item,
                    onItemClick = {

                    }
                )
            }
        }

        when {
            pagingProduct.loadState.refresh is LoadState.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            pagingProduct.loadState.append is LoadState.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            pagingProduct.loadState.refresh is LoadState.Error -> {
                item {
                    val throwable = (pagingProduct.loadState.refresh as LoadState.Error).error
                    FailureScreen(throwable.message.orEmpty())
                }
            }

            pagingProduct.loadState.append is LoadState.Error -> {
                item {
                    val throwable = (pagingProduct.loadState.append as LoadState.Error).error
                    FailureScreen(throwable.message.orEmpty())
                }
            }

        }
    }
}

@Composable
fun ProductListScreen (name: String, productList: List<ProductItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(6.dp)
    ) {
        item {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.size(6.dp))
        }
        items(productList) {
            ProductItemVerticalScreen(
                productItem = it,
                onItemClick = {

                }
            )
        }

    }

}