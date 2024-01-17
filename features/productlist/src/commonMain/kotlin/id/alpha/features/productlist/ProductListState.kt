package id.alpha.features.productlist

import androidx.paging.PagingData
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ProductListState (
    val categoryName: String = "",
    val asyncProductList: Async<List<ProductItem>> = Async.Default,
    val pagingData: Flow<PagingData<ProductItem>> = emptyFlow()
)