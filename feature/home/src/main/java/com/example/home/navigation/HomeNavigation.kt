package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = HomeRoute.HOME,
        route = HomeRoute.HOME_NAV_GRAPH,
    ){

    }
}

object HomeRoute {
    const val HOME_NAV_GRAPH = "homeNavGraph"

    const val HOME = "home"
    const val MY_BOX = "myBox"
}