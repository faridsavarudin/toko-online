package id.alpha.apis.product

import id.alpha.apis.product.model.Mapper
import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.category.CategoryResponse
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.apis.product.model.productlist.ProductListResponse
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.repository.Repository
import id.alpha.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val appConfig: AppConfig) : Repository() {
    private val dataSources by lazy { ProductDataSources(appConfig) }

    fun getAppName() = appConfig.appName

    fun getCategoryList(): Flow<Async<List<CategoryItem>>> {
        return suspend {
            dataSources.getCategoryList()
        }.reduce<CategoryResponse, List<CategoryItem>> { response ->
            val responseData = response.data

            if (responseData.isNullOrEmpty()) {
                val throwable = Throwable("Category is empty")
                Async.Failure(throwable)
            } else {
                val data = Mapper.mapResponseToCategoryList(response)
                    .take(5)
                Async.Success(data)
            }
        }
    }

    fun getProductList(query: String): Flow<Async<List<ProductItem>>> {
        return suspend {
            dataSources.getProductList(query)
        }.reduce<ProductListResponse, List<ProductItem>> { response ->
            val responseData = response.data
            if (responseData.isNullOrEmpty()) {
                val throwable = Throwable("Product is empty")
                Async.Failure(throwable)
            } else {
                val data = Mapper.mapResponseToProductList(response)
                Async.Success(data)
            }
        }
    }


}