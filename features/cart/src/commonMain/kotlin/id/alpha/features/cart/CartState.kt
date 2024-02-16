package id.alpha.features.cart

import id.alpha.apis.product.model.cart.CartProductItem
import id.alpha.libraries.core.state.Async

data class CartState(
    val asyncCart: Async<List<CartProductItem>> = Async.Default
)