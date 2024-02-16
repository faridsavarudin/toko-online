package id.alpha.features.productdetail

import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.libraries.core.state.Intent

sealed class ProductDetailIntent : Intent {

    data class GetProductDetail(val id: Int) : ProductDetailIntent()
    data class ToggleFavorite(val productDetail: ProductDetail) : ProductDetailIntent()
}