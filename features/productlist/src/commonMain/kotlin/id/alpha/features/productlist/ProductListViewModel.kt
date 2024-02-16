package id.alpha.features.productlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import id.alpha.apis.product.ProductRepository
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val appConfig: AppConfig
) : ViewModel<ProductListState, ProductListIntent> (ProductListState()) {

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is ProductListIntent.GetProductList -> {
                val categoryId = intent.categoryId
            }

            is ProductListIntent.SetCategoryName -> {

            }
        }
    }

    private fun getProductList(categoryId: Int) = viewModelScope.launch {
        val query = if (categoryId == -1) {
            ""
        } else {
            "?categoryId=$categoryId"
        }

        Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            ProductListPagingSources(appConfig, query)
        }.flow.cachedIn(viewModelScope)
            .also {
                updateUiState {
                    copy(
                        pagingData = it
                    )
                }
            }
    }

    private fun setCategoryName(name: String) {
        updateUiState {
            copy(
                categoryName = name
            )
        }
    }
}