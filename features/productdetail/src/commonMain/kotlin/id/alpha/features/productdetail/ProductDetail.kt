package id.alpha.features.productdetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductDetail (id: Int) {

    Text(
        text = id.toString()
    )
}