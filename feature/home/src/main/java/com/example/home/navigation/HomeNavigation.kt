package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.home.home.HomeScreen
import com.example.home.navigation.HomeRoute.HOME
import com.example.home.settings.SettingsScreen
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit
) {
    navigation(
        startDestination = HomeRoute.HOME,
        route = HomeRoute.HOME_NAV_GRAPH,
    ) {
        asRootComposable(
            route = HOME
        ){
            HomeScreen(
                navController = navController,
                moveToCreateBox = moveToCreateBox,
                moveToBoxDetail = moveToBoxDetail
            )
        }

        asPagingComposable(
            route = HomeRoute.SETTING
        ){
            SettingsScreen(
                navController = navController,
            )
        }
    }
}

object HomeRoute {
    const val HOME_NAV_GRAPH = "homeNavGraph"

    const val HOME = "home"
    const val MY_BOX = "myBox"
    const val SETTING = "setting"
}