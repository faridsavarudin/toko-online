package id.alpha.apis.product

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform