package id.alpha.features.favorite

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform