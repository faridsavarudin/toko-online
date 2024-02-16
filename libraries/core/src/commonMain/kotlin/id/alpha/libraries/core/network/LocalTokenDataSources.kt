package id.alpha.libraries.core.network

import androidx.compose.runtime.compositionLocalOf
import id.alpha.libraries.core.local.ValueDataSources

class LocalTokenDataSources(
    private val valueDataSources: ValueDataSources
): TokenDataSources {

    override val getToken: String
        get() = valueDataSources.getString("token")

    fun saveToken(token: String) {
        valueDataSources.setString("token", token)
    }
}

val LocalLocalTokenDataSources = compositionLocalOf<LocalTokenDataSources>
{ error("Local token data sources not provided")  }