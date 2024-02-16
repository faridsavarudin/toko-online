package id.alpha.apis.authentication

object Mapper {

    fun mapResponseToLogin(loginResponseResponse: LoginResponseResponse.DataResponse?): Login {
        return Login(token = loginResponseResponse?.token.orEmpty()
        )
    }
}