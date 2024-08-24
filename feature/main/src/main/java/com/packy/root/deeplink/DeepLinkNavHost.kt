package com.packy.root.deeplink

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.packy.root.LaunchScreen
import com.packy.root.deeplink.DeepLinkController.CreateBox.getDeepLinkController
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
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkRoute.MAIN
                },
                navDeepLink {
                    uriPattern = DeepLinkRoute.CREATE_BOX
                },
            )
        ) { backStackEntry ->
            val deepLinkController = getDeepLinkController(backStackEntry)
            LaunchScreen(
                navController = navController,
                deepLinkController = deepLinkController
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