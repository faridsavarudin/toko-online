package id.alpha.features.home

import androidx.compose.material.SnackbarHostState
import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.ProductList
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import id.alpha.libraries.core.viewmodel.ViewModelPlatform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) :
    ViewModel<HomeState, HomeIntent>(HomeState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is HomeIntent.SetName -> {
                updateName(intent.name)
            }
            is HomeIntent.GetProductList -> {
                getProductList()
            }
            is HomeIntent.ShowSnackbar -> {
                intent.coroutineScope.launch {
                    intent.snackbarState.showSnackbar(intent.name)
                }
            }
        }
    }

    private fun getProductList() = viewModelScope.launch {
        productRepository
            .getProductList()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProductList = it)
                }
            }
    }

    private fun updateName(name: String) = viewModelScope.launch {
        updateUiState {
            copy(name = name)
        }
    }
}