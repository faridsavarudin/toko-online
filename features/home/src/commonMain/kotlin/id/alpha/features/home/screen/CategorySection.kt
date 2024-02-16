package id.alpha.features.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import id.alpha.features.home.state.HomeState
import id.alpha.libraries.core.state.Async
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.alpha.apis.product.model.category.CategoryItem
import id.alpha.libraries.component.FailureScreen
import id.alpha.libraries.component.LoadingScreen


@Composable
fun CategorySection(
    homeState: HomeState,
    tryAgainAction: () -> Unit,
    onCategoryClick: (CategoryItem) -> Unit
) {
    val stateGrid = rememberLazyGridState()

    when (val async = homeState.asyncCategoryList) {
        is Async.Loading -> {
            LoadingScreen()
        }
        is Async.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = stateGrid,
                contentPadding = PaddingValues(6.dp)
            ) {
                val categoryList = async.data
                items(categoryList) { category ->
                    Category(category, onCategoryClick)
                }
                val allProductCategory = CategoryItem(
                    id = -1,
                    name = "All Products",
                    description = ""
                )
                item {
                    Category(allProductCategory, onCategoryClick)
                }
            }
        }
        is Async.Failure -> {
            val message = async.throwable.message.orEmpty()
            FailureScreen(message, tryAgainAction)
        }

        else -> {

        }
    }
}

@Composable
fun Category(categoryItem: CategoryItem, onCategoryClick: (CategoryItem) -> Unit) {

    val colorItem by derivedStateOf {
        if (categoryItem.id == -1) {
            Color.Blue.copy(alpha = 0.3f)
        } else {
            Color.Black.copy(alpha = 0.3f)
        }
    }

    val onItemClickModifier = remember {
        Modifier.clickable { onCategoryClick.invoke(categoryItem) }
    }

    Box(
        modifier = Modifier
            .padding(6.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(color = colorItem, shape = RoundedCornerShape(6.dp))
            .then(onItemClickModifier)
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = categoryItem.name,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}