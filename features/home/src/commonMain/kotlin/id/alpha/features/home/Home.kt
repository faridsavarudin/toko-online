package id.alpha.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.features.home.screen.CategorySection
import id.alpha.features.home.screen.HeaderSection
import id.alpha.features.home.screen.ProductByRatingSection
import id.alpha.features.home.viewmodel.HomeViewModel
import id.alpha.libraries.core.viewmodel.rememberViewModel
import id.alpha.apis.product.LocalProductRepository
import id.alpha.features.home.state.HomeIntent


@Composable
fun Home(
    onItemClick: (ProductItem) -> Unit,
    onCategoryClick: (CategoryItem) -> Unit
) {
    val productRepository = LocalProductRepository.current
    val homeViewModel = rememberViewModel { HomeViewModel(productRepository) }

    val homeState by homeViewModel.uiState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column {
            HeaderSection(homeState)
            CategorySection(
                homeState = homeState,
                tryAgainAction = {
                    homeViewModel.sendIntent(HomeIntent.GetCategoryList)
                },
                onCategoryClick = onCategoryClick
            )
            ProductByRatingSection(
                homeState = homeState,
                onItemClick = {
                    onItemClick.invoke(it)
                },
                tryAgainAction = {
                    homeViewModel.sendIntent(HomeIntent.GetProductsByRating)

                }
            )
        }
    }
}