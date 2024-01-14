package id.alpha.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.features.home.screen.CategorySection
import id.alpha.features.home.screen.HeaderSection
import id.alpha.features.home.screen.ProductByRatingSection
import id.alpha.features.home.state.HomeIntent
import id.alpha.features.home.viewmodel.HomeViewModel
import id.alpha.libraries.core.LocalAppConfig
import id.alpha.libraries.core.viewmodel.rememberViewModel
import id.alpha.libraries.core.state.Async

@Composable
fun Home(
    onClickItem: (ProductItem) -> Unit
) {
    val appConfig = LocalAppConfig.current
    val productRepository = remember { ProductRepository(appConfig) }
    val homeViewModel = rememberViewModel { HomeViewModel(productRepository) }

    val homeState by homeViewModel.uiState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.sendIntent(
            HomeIntent.GetCategoryList
        )
        homeViewModel.sendIntent(
            HomeIntent.GetProductsByRating
        )
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column {
            HeaderSection(homeState)
            CategorySection(homeState)
            ProductByRatingSection(homeState)
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