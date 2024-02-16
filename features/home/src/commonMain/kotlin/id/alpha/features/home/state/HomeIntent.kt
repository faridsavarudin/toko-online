package id.alpha.features.home.state

import androidx.compose.material.SnackbarHostState
import id.alpha.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope

sealed class HomeIntent : Intent {
    data object GetCategoryList : HomeIntent()
    data object GetProductsByRating : HomeIntent()
    data class ShowSnackbar(val name: String, val snackbarState: SnackbarHostState, val coroutineScope: CoroutineScope) : HomeIntent()
}