package id.alpha.apis.authentication

import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.LocalTokenDataSources
import id.alpha.libraries.core.network.NetworkDataSources
import io.ktor.client.statement.HttpResponse

class AuthenticationDataSources(
    appConfig: AppConfig,
    tokenDataSources: LocalTokenDataSources
) : NetworkDataSources(appConfig.baseUrl, tokenDataSources) {

    suspend fun login(name: String, password: String): HttpResponse {
        val loginRequest = LoginRequest(name, password)
        return postHttpResponse("/auth/login",loginRequest)
    }

    suspend fun register(name: String, password: String): HttpResponse {
        val registerRequest = RegisterRequest(name, password)
        return postHttpResponse("/auth/register", registerRequest)
    }
}