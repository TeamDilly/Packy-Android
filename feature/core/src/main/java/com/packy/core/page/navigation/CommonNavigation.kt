package com.packy.core.page.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.packy.core.animations.asRootComposable
import com.packy.core.navigiation.NavScreens
import com.packy.core.navigiation.replaceArguments
import com.packy.core.page.navigation.CommonRouteArgs.WEB_URL_ARGS
import com.packy.core.page.webscreen.WebScreen

fun NavGraphBuilder.commonNavGraph(
    navController: NavHostController,
) {
    asRootComposable(
        route = CommonScreen.WebView.name,
        arguments = listOf(
            navArgument(WEB_URL_ARGS){
                nullable = true
                type = NavType.StringType
                defaultValue = null
            }
        )
    ) {
        val url = it.arguments?.getString(WEB_URL_ARGS)
        if (url == null) {
            // TODO ErrorPage
        } else {
            WebScreen(
                navController = navController,
                url = url
            )
        }
    }
}

sealed class CommonScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) : NavScreens(_route = route, _navArguments = navArguments) {
    data object CommonNavGraph : CommonScreen(route = "commonNavGraph")
    data object WebView : CommonScreen(
        route = "WebScreen",
        navArguments = listOf(
            navArgument(WEB_URL_ARGS) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        fun create(webViewUrl: String) = name.replaceArguments(navArguments.first(), webViewUrl)
    }
}

object CommonRouteArgs {
    const val WEB_SCREEN = "WebScreen"
    const val WEB_URL_ARGS = "WebUrlArgs"
}