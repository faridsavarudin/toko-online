package id.alpha.features.home

import id.alpha.apis.product.ProductRepository
import id.alpha.apis.product.model.ProductList
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val productList = MutableStateFlow<List<ProductList>>(emptyList())

    fun getProductList() = viewModelScope.launch {
        productRepository
            .getProductList()
            .stateIn(this)
            .collect(productList)
    }
}