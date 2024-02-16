package id.alpha.apis.product.model.productlist

import id.alpha.apis.product.model.category.CategoryItem

data class ProductItem(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val discount: Int,
    val rating: Double,
    val category: CategoryItem
)