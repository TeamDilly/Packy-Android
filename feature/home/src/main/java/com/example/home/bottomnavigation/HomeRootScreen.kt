package com.example.home.bottomnavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.home.home.HomeScreen
import com.example.home.mybox.MyBoxScreen
import com.example.home.navigation.HomeRoute
import com.packy.feature.core.R

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun HomeRootScreen(
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
) {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.home),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.home_fill),
            route = HomeRoute.HOME
        ),
        BottomNavigationItem(
            title = "선물박스",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.gift),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.giftbox_fill),
            route = HomeRoute.MY_BOX
        ),
        BottomNavigationItem(
            title = "모아보기",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.archivebox),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.archivebox_fill),
            route = HomeRoute.HOME
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = null,
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = HomeRoute.HOME
        ) {
            composable(
                route = HomeRoute.HOME
            ) {
                HomeScreen(
                    navController = navController,
                    moveToCreateBox = moveToCreateBox,
                    moveToBoxDetail = moveToBoxDetail
                )
            }
            composable(
                route = HomeRoute.MY_BOX
            ) {
                MyBoxScreen(
                    navController = navController,
                    moveToCreateBox = moveToCreateBox,
                    moveToBoxDetail = moveToBoxDetail
                )
            }
        }
    }
}