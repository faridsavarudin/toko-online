package id.alpha.features.productlist

import id.alpha.libraries.core.state.Intent


sealed class ProductListIntent : Intent {

    data class GetProductList(val categoryId: Int) : ProductListIntent()
    data class SetCategoryName(val name: String) : ProductListIntent()
}