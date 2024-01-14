package id.alpha.features.home.viewmodel

import id.alpha.apis.product.ProductRepository
import id.alpha.features.home.state.HomeIntent
import id.alpha.features.home.state.HomeState
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) :
    ViewModel<HomeState, HomeIntent>(HomeState()) {

    init {
        updateUiState {
            copy(
                appName = productRepository.getAppName()
            )
        }
    }

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is HomeIntent.GetCategoryList -> {
                getCategoryList()
            }
            is HomeIntent.GetProductsByRating -> {
                getProductListByRating()
            }
            is HomeIntent.ShowSnackbar -> {
                intent.coroutineScope.launch {
                    intent.snackbarState.showSnackbar(intent.name)
                }
            }
        }
    }

    private fun getCategoryList() = viewModelScope.launch {
        productRepository
            .getCategoryList()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncCategoryList = it
                    )
                }
            }
    }

    private fun getProductListByRating() = viewModelScope.launch {
        productRepository
            .getProductList(QUERY_RATING)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProductListByRating = it)
                }
            }
    }


    companion object {
        private const val QUERY_RATING = "?sort=rating"
    }
}