package id.alpha.libraries.core.network

interface TokenDataSources {

    val getToken: String

    companion object {
        val Default = object : TokenDataSources {
            override val getToken: String
                get() = ""

        }
    }
}