package com.packy.createbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.createbox.boxaddinfo.BoxAddInfoScreen
import com.packy.createbox.boxchoice.BoxChoiceScreen
import com.packy.createbox.boxguide.BoxGuideScreen


fun NavGraphBuilder.createBoxNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = CreateBoxRoute.BOX_ADD_INFO,
        route = CreateBoxRoute.CREATE_BOX_NAV_GRAPH,
    ){
        asRootComposable(
            route = CreateBoxRoute.BOX_ADD_INFO,

        ){
            BoxAddInfoScreen(navController = navController)
        }
        asPagingComposable(
            route = CreateBoxRoute.BOX_CHOICE,
        ){
            BoxChoiceScreen(navController = navController)
        }
        asPagingComposable(
            route = CreateBoxRoute.BOX_GUIDE,
        ){
            BoxGuideScreen(navController = navController)
        }
    }
}

object CreateBoxRoute{
    const val CREATE_BOX_NAV_GRAPH = "createBoxNavGraph"

    const val BOX_CHOICE = "boxChoice"
    const val BOX_GUIDE = "boxGuide"
    const val BOX_ADD_INFO = "boxAddInfo"
}