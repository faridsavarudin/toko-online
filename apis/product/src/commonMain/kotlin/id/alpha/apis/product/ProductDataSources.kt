package id.alpha.apis.product

import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse

class ProductDataSources(
    private val appConfig: AppConfig) : NetworkDataSources(appConfig.baseUrl) {

    suspend fun getProductList(): HttpResponse {
        val endPoint = "product"
        return getHttpResponse(endPoint)
    }
}