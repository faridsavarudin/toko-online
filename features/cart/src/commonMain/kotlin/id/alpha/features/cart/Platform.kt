package id.alpha.features.cart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform