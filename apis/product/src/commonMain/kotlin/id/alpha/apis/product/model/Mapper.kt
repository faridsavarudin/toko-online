package id.alpha.apis.product.model

import id.alpha.apis.product.model.cart.CartProductItem
import id.alpha.apis.product.model.cart.CartResponse
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.category.CategoryResponse
import id.alpha.apis.product.model.local.ProductRealm
import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.apis.product.model.productdetail.ProductDetailResponse
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

    fun mapItemResponseToItemList(itemResponse: ProductListResponse.DataResponse?): ProductItem {
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

    fun mapResponseToDetail(productDetailResponse: ProductDetailResponse.DataResponse): ProductDetail {
        return ProductDetail(
            id = productDetailResponse.id ?: 0,
            name = productDetailResponse.name.orEmpty(),
            description = productDetailResponse.description.orEmpty(),
            price = productDetailResponse.price ?: 0.0,
            image = productDetailResponse.images?.get(0).orEmpty()
        )
    }

    fun realmMapFromDetail(detail: ProductDetail): ProductRealm {
        return ProductRealm()
            .apply {
                id = detail.id
                name = detail.name
                price = detail.price
                description = detail.description
                image = detail.image
            }
    }

    fun realmMapToItem(realm: ProductRealm): ProductItem {
        return ProductItem(
            id = realm.id,
            name = realm.name,
            price = realm.price,
            image = realm.image,
            discount = 0,
            rating = 0.0,
            category = CategoryItem(0, "", "")
        )
    }

    fun mapResponseToCartProductItem(response: List<CartResponse.DataResponse>?) : List<CartProductItem> {
        return response?.map {
            CartProductItem(
                productId = it.productId ?: 0,
                price = it.price ?: 0.0,
                discount = it.discount ?: 0,
                amount = it.amount ?: 0.0,
                quantity = it.quantity ?: 0
            )
        }.orEmpty()
    }
}