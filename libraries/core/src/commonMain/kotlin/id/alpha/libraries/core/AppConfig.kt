package id.alpha.libraries.core

import androidx.compose.runtime.compositionLocalOf

interface AppConfig {

    val appName: String
    val baseUrl: String
}

val LocalAppConfig = compositionLocalOf<AppConfig>{ error("AppConfig not provided") }