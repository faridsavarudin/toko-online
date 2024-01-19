package id.alpha.features.productdetail

import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.libraries.core.state.Async

data class ProductDetailState (
    val asyncProductDetail: Async<ProductDetail> = Async.Default
)