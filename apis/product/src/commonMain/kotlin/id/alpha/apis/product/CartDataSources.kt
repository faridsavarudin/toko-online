package id.alpha.apis.product

import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.LocalTokenDataSources
import id.alpha.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse

class CartDataSources(
    appConfig: AppConfig,
    localTokenDataSources: LocalTokenDataSources
) : NetworkDataSources(appConfig.baseUrl, localTokenDataSources) {

    suspend fun getCart(): HttpResponse {
        val endpoint = "/cart"
        return getHttpResponse(endpoint)
    }
}