package id.alpha.features.login

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform