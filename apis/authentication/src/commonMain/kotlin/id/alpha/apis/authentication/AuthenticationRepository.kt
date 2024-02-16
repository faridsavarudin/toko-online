package id.alpha.apis.authentication

import androidx.compose.runtime.compositionLocalOf
import id.alpha.libraries.core.AppConfig
import id.alpha.libraries.core.network.LocalTokenDataSources
import id.alpha.libraries.core.repository.Repository
import id.alpha.libraries.core.state.Async
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val appConfig: AppConfig,
    private val localTokenDataSources: LocalTokenDataSources
) : Repository() {

    private val dataSources by lazy { AuthenticationDataSources(appConfig, localTokenDataSources) }

    fun login(name: String, password: String): Flow<Async<Login>> {
        return suspend {
            dataSources
                .login(name, password)
        }.reduce<LoginResponseResponse, Login> { response ->
            val responseData = response.data

            if (responseData == null) {
                Async.Failure(Throwable("Data invalid"))
            } else {
                val data = Mapper.mapResponseToLogin(responseData)
                localTokenDataSources.saveToken(data.token)
                Async.Success(data)
            }
        }
    }

    fun register(name: String, password: String): Flow<Async<String>> {
        return suspend {
            dataSources
                .register(name, password)
        }.reduce<RegisterResponse, String> { response ->
            Async.Success(response.message.orEmpty())
        }
    }
}

val LocalAuthenticationRepository = compositionLocalOf<AuthenticationRepository> { error("auth repo not provided!") }
