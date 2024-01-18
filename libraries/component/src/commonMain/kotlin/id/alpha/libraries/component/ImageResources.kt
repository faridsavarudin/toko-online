package id.alpha.libraries.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.painter.Painter

interface ImageResources {

    @Composable
    fun ArrowBack(): Painter
}

val LocalImageResource = compositionLocalOf <ImageResources> { error("Image resource not provided") }