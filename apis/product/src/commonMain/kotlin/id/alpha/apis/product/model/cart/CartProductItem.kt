package id.alpha.apis.product.model.cart

data class CartProductItem(
    val productId: Int = -1,
    val price: Double = 0.0,
    val discount: Int = 0,
    val amount: Double = 0.0,
    val quantity: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
)