package id.alpha.apis.product

import id.alpha.apis.product.model.Mapper
import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.ProductListResponse
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository {
    private val dataSources by lazy { ProductDataSources() }

    suspend fun getProductList(): Flow<List<ProductList>> {
        val data = dataSources
            .getProductList()
            .body<ProductListResponse>()
            .let { Mapper.mapResponseToList(it) }
        return flow {
            emit(data)
        }
    }
}