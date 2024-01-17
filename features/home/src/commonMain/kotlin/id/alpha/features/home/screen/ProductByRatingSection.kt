package id.alpha.features.home.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.features.home.state.HomeState
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.component.product.ProductItemGridScreen
import id.alpha.libraries.core.state.Async

@Composable
fun ProductByRatingSection(
    homeState: HomeState,
    onItemClick: (ProductItem) -> Unit,
    tryAgainAction: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp)
            .padding(top = 12.dp)
    ) {
        Text(
            text = "Top Products"
        )
    }
    when (val async = homeState.asyncProductListByRating) {
        is Async.Loading -> {
            LoadingScreen()
        }

        is Async.Success -> {
            val products = async.data
            LazyRow(
                contentPadding = PaddingValues(6.dp)
            ) {
                items(products) {
                    ProductItemGridScreen(
                        productItem = it,
                        onItemClick = onItemClick
                    )
                }
            }
        }
        is Async.Failure -> {
            val message = async.throwable.message.orEmpty()
            FailureScreen(message, tryAgainAction)
        }
        else -> {}
    }
}