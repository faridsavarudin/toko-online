package id.alpha.features.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import id.alpha.apis.product.LocalProductRepository
import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.libraries.component.AppTopBar
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen
import id.alpha.libraries.component.LocalImageResource
import id.alpha.libraries.component.utils.toRupiah
import id.alpha.libraries.core.state.Async
import id.alpha.libraries.core.viewmodel.rememberViewModel

@Composable
fun ProductDetail (
    id: Int,
    actionBack: () -> Unit
) {
    val repository = LocalProductRepository.current
    val viewModel = rememberViewModel { ProductDetailViewModel(repository) }
    val state by viewModel.uiState.collectAsState()

    val imageResources = LocalImageResource.current

    val imageFavoriteResource = if (state.isFavorite) {
        imageResources.StarFill()
    } else {
        imageResources.StarBorder()
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ProductDetailIntent.GetProductDetail(id))
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "",
                actionBack = actionBack
            )
        }
    ) {
        LazyColumn {
            when (val async = state.asyncProductDetail) {
                is Async.Loading -> {
                    item {
                        LoadingScreen()
                    }
                }
                is Async.Failure -> {
                    item {
                        val message = async.throwable.message.orEmpty()
                        FailureScreen(message)
                    }
                }
                is Async.Success -> {
                    val data = async.data
                    renderSuccessDetail(data, imageFavoriteResource) {
                        viewModel.sendIntent(ProductDetailIntent.ToggleFavorite(data))
                    }

                }
                else -> {}
            }
        }
    }
    Text(
        text = id.toString()
    )
}

fun LazyListScope.renderSuccessDetail(
    data: ProductDetail,
    imageFavorite: Painter, toggleFavorite: () -> Unit) {
    item {
        Image(
            painter = rememberImagePainter(data.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
        )
    }

    item {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = data.name,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )

            // icon favorite
            IconButton(
                onClick = {
                    toggleFavorite.invoke()
                }
            ) {
                Icon(
                    painter = imageFavorite,
                    contentDescription = null
                )
            }
        }
    }

    item {
        Text(
            text = data.price.toRupiah,
            modifier = Modifier.padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        )
    }

    item {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = data.description
            )
        }
    }
}