package com.example.home.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.home.archive.ArchiveScreen
import com.example.home.home.HomeScreen
import com.example.home.mybox.MyBoxScreen
import com.packy.core.animations.asFadeInComposable
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun HomeRootScreen(
    modifier: Modifier = Modifier,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    moveSettings: () -> Unit,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.home_fill),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.home),
            route = HomeRoute.HOME
        ),
        BottomNavigationItem(
            title = "선물박스",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.giftbox_fill),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.giftbox),
            route = HomeRoute.MY_BOX
        ),
        BottomNavigationItem(
            title = "모아보기",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.archivebox_fill),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.archivebox),
            route = HomeRoute.ARCHIVE
        )
    )

    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = HomeRoute.HOME
        ) {
            asFadeInComposable(
                route = HomeRoute.HOME
            ) {
                HomeScreen(
                    navController = navController,
                    moveToCreateBox = moveToCreateBox,
                    moveToBoxDetail = moveToBoxDetail,
                    moveSettings = moveSettings
                )
            }
            asFadeInComposable(
                route = HomeRoute.MY_BOX
            ) {
                MyBoxScreen(
                    navController = navController,
                    moveToCreateBox = moveToCreateBox,
                    moveToBoxDetail = moveToBoxDetail
                )
            }
            asFadeInComposable(
                route = HomeRoute.ARCHIVE
            ) {
                ArchiveScreen(
                    navController = navController
                )
            }
        }
        Row(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavigationItems.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(PackyTheme.color.white)
                        .clickableWithoutRipple {
                            navController.navigate(item.route) {
                                navController.currentBackStackEntry?.destination?.route?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                        inclusive = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

object HomeRoute {
    const val HOME_ROOT = "homeRootRoute"
    const val HOME = "home"
    const val MY_BOX = "myBox"
    const val ARCHIVE = "archive"
}