package id.alpha.apis.product

import id.alpha.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse

class ProductDataSources : NetworkDataSources("https://marketfake.fly.dev/") {

    suspend fun getProductList(): HttpResponse {
        val endPoint = "product"
        return getHttpResponse(endPoint)
    }
}