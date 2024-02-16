import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import id.alpha.libraries.component.ImageResources
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
class ImageResourcesProvider: ImageResources {

    @Composable
    override fun ArrowBack(): Painter {
        return painterResource("baseline_arrow_back_24.xml")
    }

    @Composable
    override fun StarFill(): Painter {
        return painterResource("round_star_24.xml")
    }

    @Composable
    override fun StarBorder(): Painter {
        return painterResource("round_star_border_24.xml")
    }
}