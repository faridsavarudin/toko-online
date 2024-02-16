import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import id.alpha.features.cart.Cart
import id.alpha.features.favorite.Favorite
import id.alpha.features.home.Home
import id.alpha.libraries.component.utils.toJson
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

enum class Tabb {
    HOME, FAVORITE, CART
}

class TabNavigatorr {
    var currentTab by mutableStateOf(Tabb.HOME)
}

val LocalTabNavigatorr = compositionLocalOf<TabNavigatorr> { error("Tab navigator not provided") }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.BottomTabItem(tab: Tabb, pagerState: PagerState) {
    val tabNavigator = LocalTabNavigatorr.current

    val isSelected by derivedStateOf { tabNavigator.currentTab == tab }
    val scope = rememberCoroutineScope()

    BottomNavigationItem(
        selected = isSelected,
        onClick = {
            val page = when (tab) {
                Tabb.HOME -> 0
                Tabb.FAVORITE -> 1
                Tabb.CART -> 2
            }

            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        },
        icon = {},
        label = {
            Text(
                text = tab.name.lowercase()
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreenn(navigator: Navigator) {

    val pagerState = rememberPagerState { 3 }
    val tabNavigator = LocalTabNavigatorr.current

    when (pagerState.currentPage) {
        0 -> {
            tabNavigator.currentTab = Tabb.HOME
        }
        1 -> {
            tabNavigator.currentTab = Tabb.FAVORITE
        }
        2 -> {
            tabNavigator.currentTab = Tabb.CART
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomTabItem(Tabb.HOME, pagerState)
                BottomTabItem(Tabb.FAVORITE, pagerState)
                BottomTabItem(Tabb.CART, pagerState)
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 2
        ) { index ->
            when (index) {
                0 -> {
                    Home(
                        onItemClick = {
                            println("asuuuuuuuuuuu item")
                            navigator.navigate("/detail/${it.id}")
                        },
                        onCategoryClick = {
                            val json = it.toJson()
                            println("asuuuuuuuuuuu category")
                            navigator.navigate("/list/$json")
                        }
                    )
                }
                1 -> {
                    Favorite(
                        onItemClick = {
                            navigator.navigate("/detail/${it.id}")
                        }
                    )
                }
                2 -> {
                    Cart {
                        navigator.navigate(
                            route = "/login",
                            options = NavOptions(
                                launchSingleTop = true,
                                popUpTo = PopUpTo.First(true)
                            )
                        )
                    }
                }
            }
        }
    }
}