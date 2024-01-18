package id.alpha.libraries.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppTopBar(title: String, actionBack: () -> Unit = {}) {
    val imageResources = LocalImageResource.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        IconButton(
            onClick = {
                actionBack.invoke()
            }
        ) {
            Icon(
                painter = imageResources.ArrowBack(),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }

}