package id.alpha.features.productdetail

import id.alpha.apis.product.ProductRepository
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository
) : ViewModel<ProductDetailState, ProductDetailIntent>(ProductDetailState()){

    override fun sendIntent(intent: Intent) {
        when(intent) {
            is ProductDetailIntent.GetProductDetail -> {
                val id = intent.id
                getProductDetail(id)
            }
            is ProductDetailIntent.ToggleFavorite -> {
                val id = intent.id
                toggleFavorite(id)
            }
        }
    }

    private fun getProductDetail(id: Int) = viewModelScope.launch {
        repository.getProductDetail(id)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncProductDetail = it
                    )
                }
            }
    }

    private fun toggleFavorite(id: Int) = viewModelScope.launch {

    }

}