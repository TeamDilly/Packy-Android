package com.packy.core.page.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.packy.core.animations.asRootComposable
import com.packy.core.page.navigation.CommonRoute.WEB_URL_ARGS
import com.packy.core.page.webscreen.WebScreen

fun NavGraphBuilder.commonNavGraph(
    navController: NavHostController,
) {
    asRootComposable(
        route = CommonRoute.WEB_SCREEN + "/{$WEB_URL_ARGS}"
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

object CommonRoute {
    internal const val WEB_SCREEN = "WebScreen"
    internal const val WEB_URL_ARGS = "WebUrlArgs"
    fun getWebScreenRoute(url: String) = WEB_SCREEN + "/${url}"
}