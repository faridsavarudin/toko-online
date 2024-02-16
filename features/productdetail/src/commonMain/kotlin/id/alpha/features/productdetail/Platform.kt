package id.alpha.features.productdetail

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform