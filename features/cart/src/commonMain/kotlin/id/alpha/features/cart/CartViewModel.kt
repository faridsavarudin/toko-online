package id.alpha.features.cart

import id.alpha.apis.product.ProductRepository
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val productRepository: ProductRepository
 ) : ViewModel<CartState, CartIntent>(CartState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is CartIntent.GetCart -> {
                getCart()
            }
        }
    }

    private fun getCart() = viewModelScope.launch {
        productRepository.getCart()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(
                        asyncCart = it
                    )
                }
            }

    }
}