package id.alpha.apis.product

import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay

class ProductDataSources(
    private val appConfig: AppConfig) : NetworkDataSources(appConfig.baseUrl) {

    suspend fun getCategoryList(): HttpResponse {
        val endPoint = "product/category"
        delay(2000)
        return getHttpResponse(endPoint)
    }

    suspend fun getProductList(query: String): HttpResponse {
        val endPoint = "product$query"
        delay(2000)
        return getHttpResponse(endPoint)
    }
}