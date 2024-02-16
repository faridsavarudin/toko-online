package splash

import id.alpha.libraries.core.local.ValueDataSources
import id.alpha.libraries.core.state.Intent
import id.alpha.libraries.core.viewmodel.ViewModel

class SplashViewModel(
    private val valueDataSources: ValueDataSources

) : ViewModel<SplashState, SplashIntent>(SplashState()){

    override fun sendIntent(intent: Intent) {
        when (intent) {
            is SplashIntent.CheckToken -> {
                checkToken()
            }
        }
    }

    private fun checkToken() {
        val currentToken = valueDataSources.getString("token")
        val isHasToken = currentToken.isNotEmpty()
        updateUiState {
            copy(
                isHasToken = isHasToken,
                isHasCheckToken = true
            )
        }
    }


}