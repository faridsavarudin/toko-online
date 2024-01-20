package id.alpha.features.productdetail

import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.productdetail.ProductDetail
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
                getProductIsFavorite(id)
            }
            is ProductDetailIntent.ToggleFavorite -> {
                val detail = intent.productDetail
                toggleFavorite(detail)
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

    private fun getProductIsFavorite(id: Int) = viewModelScope.launch {
        repository.isProductFavorite(id)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        isFavorite = it
                    )
                }
            }
    }

    private fun toggleFavorite(productDetail: ProductDetail) = viewModelScope.launch {
        if (uiState.value.isFavorite) {
            repository.deleteFavorite(productDetail.id)
        } else {
            repository.insertFavorite(productDetail)
        }
    }

}