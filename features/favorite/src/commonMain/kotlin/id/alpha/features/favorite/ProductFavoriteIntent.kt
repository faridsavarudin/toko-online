package id.alpha.features.favorite

import id.alpha.libraries.core.state.Intent

sealed class ProductFavoriteIntent : Intent {
    data object GetFavorite : ProductFavoriteIntent()
}