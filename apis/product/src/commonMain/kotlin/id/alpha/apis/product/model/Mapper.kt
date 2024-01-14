package id.alpha.apis.product.model

import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.category.CategoryResponse
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.apis.product.model.productlist.ProductListResponse

object Mapper {

    fun mapResponseToProductList(productListResponse: ProductListResponse?) : List<ProductItem> {
        return productListResponse
            ?.data
            ?.map {
                mapItemResponseToItemList(it)
            }.orEmpty()
    }

    private fun mapItemResponseToItemList(itemResponse: ProductListResponse.DataResponse?): ProductItem {
        return ProductItem(
            id = itemResponse?.id ?: 0,
            name = itemResponse?.name.orEmpty(),
            price = itemResponse?.price ?: 0.0,
            image = itemResponse?.images.orEmpty(),
            discount = itemResponse?.discount ?: 0,
            rating = itemResponse?.rating ?: 0.0,
            category = (CategoryItem(
                id = itemResponse?.category?.id ?: 0,
                name = itemResponse?.category?.name.orEmpty(),
                description = itemResponse?.category?.description.orEmpty(),
            ))
        )
    }

    fun mapResponseToCategoryList(categoryResponse: CategoryResponse?) : List<CategoryItem> {
        return categoryResponse?.data?.map {
            mapItemCategoryResponseToCategory(it)
        }.orEmpty()
    }

    private fun mapItemCategoryResponseToCategory(itemResponse: CategoryResponse.DataResponse?) : CategoryItem {
        return CategoryItem(
            id = itemResponse?.id ?: 0,
            name = itemResponse?.name.orEmpty(),
            description = itemResponse?.description.orEmpty(),
        )
    }
}