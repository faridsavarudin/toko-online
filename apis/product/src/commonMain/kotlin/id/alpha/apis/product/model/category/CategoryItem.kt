package id.alpha.apis.product.model.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryItem(
    val id: Int,
    val name: String,
    val description: String
)