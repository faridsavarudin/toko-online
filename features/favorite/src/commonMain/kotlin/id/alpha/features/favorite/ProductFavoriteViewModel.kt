package id.alpha.features.favorite

import id.alpha.apis.product.ProductRepository
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductFavoriteViewModel(
    private val productRepository: ProductRepository
) : ViewModel<ProductFavoriteState, ProductFavoriteIntent>(ProductFavoriteState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is ProductFavoriteIntent -> {

            }
        }
    }

    private fun getProductFavorite() = viewModelScope.launch {
        productRepository
            .getProductFavorite()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        productList = it
                    )
                }
            }
    }
}