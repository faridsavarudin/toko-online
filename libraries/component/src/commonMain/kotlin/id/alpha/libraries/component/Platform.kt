package id.alpha.libraries.component

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform