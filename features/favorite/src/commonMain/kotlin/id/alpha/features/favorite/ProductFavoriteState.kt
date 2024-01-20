package id.alpha.features.favorite

import id.alpha.apis.product.model.productlist.ProductItem

data class ProductFavoriteState (
    val productList: List<ProductItem> = emptyList()
)