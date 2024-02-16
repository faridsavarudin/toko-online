package id.alpha.apis.product

import id.alpha.apis.product.model.Mapper
import id.alpha.apis.product.model.local.ProductRealm
import id.alpha.apis.product.model.productdetail.ProductDetail
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.core.local.LocalDataSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductFavoriteDataSources : LocalDataSources(ProductRealm::class) {
    suspend fun insertProduct(productDetail: ProductDetail) {
        val realm = Mapper.realmMapFromDetail(productDetail)
        insertObject(realm)
    }

    suspend fun removeProduct(productId: Int) {
        removeObject(ProductRealm::class, productId)
    }

    suspend fun getProductIsFavorite(productId: Int): Flow<Boolean> {
        return getObjectExistById(ProductRealm::class, productId)
    }

    suspend fun getAllFavorite(): Flow<List<ProductItem>> {
        return getObjects(ProductRealm::class)
            .map {
                it.map {
                    Mapper.realmMapToItem(it)
                }
            }
    }

}