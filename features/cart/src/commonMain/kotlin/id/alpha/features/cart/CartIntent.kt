package id.alpha.features.cart

import id.alpha.libraries.core.state.Intent

sealed class CartIntent : Intent {

    data object GetCart : CartIntent()
}