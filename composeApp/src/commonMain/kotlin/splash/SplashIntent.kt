package splash

import id.alpha.libraries.core.state.Intent

sealed class SplashIntent : Intent {

    data object CheckToken : SplashIntent()
}