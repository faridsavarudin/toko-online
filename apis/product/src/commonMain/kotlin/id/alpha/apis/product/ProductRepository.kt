package id.alpha.apis.product

import id.alpha.apis.product.model.Mapper
import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.ProductListResponse
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.repository.Repository
import id.alpha.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val appConfig: AppConfig) : Repository() {
    private val dataSources by lazy { ProductDataSources(appConfig) }

    fun getProductList(): Flow<Async<List<ProductList>>> {
        return suspend {
            dataSources.getProductList()
        }.reduce<ProductListResponse, List<ProductList>> { response ->
            val responseData = response.data

            if (responseData.isNullOrEmpty()) {
                val throwable = Throwable("Product is empty")
                Async.Failure(throwable)
            } else {
                val data = Mapper.mapResponseToList(response)
                Async.Success(data)
            }
        }
    }
}