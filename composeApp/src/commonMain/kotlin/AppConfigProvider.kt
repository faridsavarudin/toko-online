import com.tokoonline.app.BuildKonfig
import id.alpha.libraries.core.AppConfig

class AppConfigProvider : AppConfig {
    override val baseUrl: String
        get() = BuildKonfig.BASE_URL
}