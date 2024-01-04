package id.alpha.features.home

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform