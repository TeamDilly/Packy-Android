package com.packy.createbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.packy.core.animations.PaginationAnimation
import com.packy.createbox.boxchoice.BoxChoiceScreen
import com.packy.createbox.boxguide.BoxGuideScreen
import com.packy.createbox.boxstart.BoxStartScreen
import kotlinx.coroutines.flow.SharingCommand


fun NavGraphBuilder.createBoxNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = CreateBoxRoute.BOX_START,
        route = CreateBoxRoute.CREATE_BOX_NAV_GRAPH
    ){
        composable(
            route = CreateBoxRoute.BOX_GUIDE,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ){
            BoxStartScreen(navController = navController)
        }
        composable(
            route = CreateBoxRoute.BOX_CHOICE,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ){
            BoxChoiceScreen(navController = navController)
        }
        composable(
            route = CreateBoxRoute.BOX_GUIDE,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ){
            BoxGuideScreen(navController = navController)
        }
    }
}

object CreateBoxRoute{
    const val CREATE_BOX_NAV_GRAPH = "createBoxNavGraph"

    const val BOX_CHOICE = "boxChoice"
    const val BOX_GUIDE = "boxGuide"
    const val BOX_START = "boxStart"
}