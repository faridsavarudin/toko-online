package id.alpha.features.productlist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform