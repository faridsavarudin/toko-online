package id.alpha.apis.authentication

data class LoginResponseResponse(
    val status: Boolean?,
    val message: String,
    val `data`: DataResponse?
) {
    data class DataResponse(
        val token: String?
    )
}