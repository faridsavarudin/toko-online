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
import id.alpha.features.favorite.Favorite
import id.alpha.features.home.Home
import id.alpha.libraries.component.utils.toJson
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator

enum class Tab {
    HOME, FAVORITE
}

class TabNavigator {
    var currentTab by mutableStateOf(Tab.HOME)
}

val LocalTabNavigator = compositionLocalOf<TabNavigator> { error("Tab navigator not provided") }

@OptIn(ExperimentalFoundationApi::class)
@Composable

fun RowScope.BottomTabItem(tab: Tab, pagerState: PagerState) {
    val tabNavigator = LocalTabNavigator.current

    val isSelected by derivedStateOf { tabNavigator.currentTab == tab }
    val scope = rememberCoroutineScope()

    BottomNavigationItem(
        selected = isSelected,
        onClick = {
            val page = when (tab) {
                Tab.HOME -> 0
                Tab.FAVORITE -> 1
            }

            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        },
        icon = {},
        label = {
            Text(
                text = tab.name
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(navigator: Navigator) {

    val pagerState = rememberPagerState { 2 }
    val tabNavigator = LocalTabNavigator.current

    when (pagerState.currentPage) {
        0 -> {
            tabNavigator.currentTab = Tab.HOME
        }
        1 -> {
            tabNavigator.currentTab = Tab.FAVORITE
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomTabItem(Tab.HOME, pagerState)
                BottomTabItem(Tab.FAVORITE, pagerState)
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
            }
        }
    }
}