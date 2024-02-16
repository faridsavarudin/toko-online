package id.alpha.apis.product

import androidx.compose.runtime.compositionLocalOf
import id.alpha.apis.product.model.Mapper
import id.alpha.apis.product.model.ProductList
import id.alpha.apis.product.model.cart.CartProductItem
import id.alpha.apis.product.model.cart.CartResponse
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.apis.product.model.category.CategoryResponse
import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.apis.product.model.productdetail.ProductDetailResponse
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.apis.product.model.productlist.ProductListResponse
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.LocalTokenDataSources
import id.alpha.libraries.core.repository.Repository
import id.alpha.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val appConfig: AppConfig,
    private val localTokenDataSources: LocalTokenDataSources
) : Repository() {
    private val dataSources by lazy { ProductDataSources(appConfig) }
    private val favoriteDataSources by lazy { ProductFavoriteDataSources() }
    private val cartDataSources by lazy {
        CartDataSources(
            appConfig = appConfig,
            localTokenDataSources = localTokenDataSources
        )
    }

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

    fun getProductDetail(productId: Int): Flow<Async<ProductDetail>> {
        return suspend {
            dataSources.getProductDetail(productId)
        }.reduce<ProductDetailResponse, ProductDetail>  { response ->
            val responseData = response.data
            if (responseData == null) {
                val throwable = Throwable("Product not found!")
                Async.Failure(throwable)
            } else {
                val data = Mapper.mapResponseToDetail(responseData)
                Async.Success(data)
            }
        }
    }

    suspend fun getProductFavorite(): Flow<List<ProductItem>> {
        return favoriteDataSources.getAllFavorite()
    }

    suspend fun isProductFavorite(productId: Int): Flow<Boolean> {
        return favoriteDataSources.getProductIsFavorite(productId)
    }

    suspend fun insertFavorite(productDetail: ProductDetail) {
        favoriteDataSources.insertProduct(productDetail)
    }

    suspend fun deleteFavorite(productId: Int) {
        favoriteDataSources.removeProduct(productId)
    }

    fun getCart(): Flow<Async<List<CartProductItem>>> {
        return suspend {
            cartDataSources.getCart()
        }.reduce<CartResponse, List<CartProductItem>> { response ->
            val responseData = response.data.orEmpty()
            if (responseData.isEmpty()) {
                Async.Failure(Throwable("Cart is empty"))
            } else {
                Async.Success(Mapper.mapResponseToCartProductItem(response.data?.filterNotNull()))
            }
        }
    }
}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("Product repository not provided!") }