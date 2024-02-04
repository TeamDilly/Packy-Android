package com.packy.root.deeplink

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.packy.root.LaunchScreen
import com.packy.root.navigation.MainRoute
import com.packy.root.navigation.MainRoute.LAUNCH_NAV_GRAPH

fun NavGraphBuilder.deepLinkNavGraph(
    navController: NavHostController,
    kakaoLinkScheme: String
) {

    navigation(
        startDestination = MainRoute.LAUNCH_ROUTE,
        route = LAUNCH_NAV_GRAPH,
    ) {

        composable(
            route = MainRoute.LAUNCH_ROUTE,
        ) {
            LaunchScreen(
                navController = navController,
                deepLinkController = DeepLinkController.NonDeepLink
            )
        }

        composable(
            route = MainRoute.LAUNCH_ROUTE_OPEN_BOX + "/{boxId}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRoute.GIFT_BOX_OPEN_LINK
                },
                navDeepLink {
                    uriPattern = "$kakaoLinkScheme://kakaolink?boxId={boxId}"
                }
            ),
        ) { entry ->
            val boxId = entry.arguments?.getString("boxId")
            boxId?.let {
                val deepLinkController = DeepLinkController.OpenBox(it)
                LaunchScreen(
                    navController = navController,
                    deepLinkController = deepLinkController
                )
            } ?: LaunchScreen(
                navController = navController,
                deepLinkController = DeepLinkController.NonDeepLink
            )
        }
    }
}