package id.alpha.features.home

import id.alpha.apis.product.model.ProductList
import id.alpha.libraries.core.state.Async


data class HomeState(
    val name: String = "",
    val asyncProductList: Async<List<ProductList>> = Async.Default
)