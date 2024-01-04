package id.alpha.api.product

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform