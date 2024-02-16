package id.alpha.apis.product.model.cart

data class CartResponse(
    val status: Boolean?,
    val message: String?,
    val `data`: List<DataResponse?>?
) {
    data class DataResponse(
        val productId: Int?,
        val price: Double?,
        val discount: Int?,
        val amount: Double?,
        val quantity: Int?,
        val createdAt: String?,
        val updatedAt: String?
    )
}