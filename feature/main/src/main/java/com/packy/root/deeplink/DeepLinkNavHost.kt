package com.packy.root.deeplink

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.packy.root.LaunchScreen
import com.packy.root.navigation.MainScreens

fun NavGraphBuilder.deepLinkNavGraph(
    navController: NavHostController,
    kakaoLinkScheme: String
) {

    navigation(
        startDestination = MainScreens.LaunchRoute.name,
        route = MainScreens.LaunchNavGraph.name,
    ) {

        composable(
            route = MainScreens.LaunchRoute.name,
            deepLinks = listOf(navDeepLink {
                uriPattern = DeepLinkRoute.MAIN
            })
        ) {
            LaunchScreen(
                navController = navController,
                deepLinkController = DeepLinkController.NonDeepLink
            )
        }

        composable(
            route = MainScreens.LaunchRouteOpenBox.name,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRoute.GIFT_BOX_OPEN_LINK
                },
                navDeepLink {
                    uriPattern = "$kakaoLinkScheme://kakaolink?boxId={boxId}"
                },
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