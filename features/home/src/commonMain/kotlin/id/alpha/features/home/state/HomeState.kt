package id.alpha.features.home.state

import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.core.state.Async


data class HomeState(
    val appName: String = "",
    val asyncProductListByRating: Async<List<ProductItem>> = Async.Default,

    val asyncCategoryList: Async<List<CategoryItem>> = Async.Default

)