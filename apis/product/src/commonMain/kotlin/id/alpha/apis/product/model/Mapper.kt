package id.alpha.apis.product.model

object Mapper {

    fun mapResponseToList(productListResponse: ProductListResponse?) : List<ProductList> {
        return productListResponse
            ?.data
            ?.map {
                mapItemResponseToItemList(it)
            }.orEmpty()
    }

    private fun mapItemResponseToItemList(itemResponse: ProductListResponse.DataResponse?): ProductList {
        return ProductList(
            id = itemResponse?.id ?: 0,
            name = itemResponse?.name.orEmpty(),
            price = itemResponse?.price ?: 0.0,
            image = itemResponse?.images.orEmpty(),
            discount = itemResponse?.discount ?: 0
        )
    }
}