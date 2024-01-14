package id.alpha.libraries.component.product


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.alpha.apis.product.model.productlist.ProductItem
import id.alpha.libraries.component.utils.toRupiah

@Composable
fun ProductItem(productItem: ProductItem) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .height(160.dp)
            .aspectRatio(1/1.5f)
            .background(
                color = Color.Black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(
                    color = Color.Blue.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(topEnd = 6.dp, topStart = 6.dp)
                )
        ) {

            Text(
                text = productItem.rating.toString(),
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(6.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
        }
        Column(
            modifier = Modifier.weight(0.6f).fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(
                text = productItem.name,
                fontSize = 12.sp
            )
            Text(
                text = productItem.category.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = productItem.price.toRupiah,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}