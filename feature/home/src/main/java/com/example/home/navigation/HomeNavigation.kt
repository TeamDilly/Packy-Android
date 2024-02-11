package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.home.bottomnavigation.HomeRootScreen
import com.example.home.navigation.HomeRoute.HOME_ROOT
import com.example.home.settingaccount.SettingAccountScreen
import com.example.home.settings.SettingsScreen
import com.example.home.withdrawal.WithdrawalScreen
import com.packy.core.animations.asPagingComposable

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    logout: () -> Unit
) {
    navigation(
        startDestination = HOME_ROOT,
        route = HomeRoute.HOME_NAV_GRAPH,
    ) {
        composable(
            route = HOME_ROOT
        ) {
            HomeRootScreen(
                moveToCreateBox = moveToCreateBox,
                moveToBoxDetail = moveToBoxDetail,
            )
        }

        asPagingComposable(
            route = HomeRoute.SETTING
        ) {
            SettingsScreen(
                navController = navController,
                logout = logout
            )
        }

        asPagingComposable(
            route = HomeRoute.SETTING_ACCOUNT
        ) {
            SettingAccountScreen(
                navController = navController
            )
        }

        asPagingComposable(
            route = HomeRoute.WITHDRAWAL
        ) {
            WithdrawalScreen(
                navController = navController,
                logout = logout
            )
        }
    }
}

object HomeRoute {
    const val HOME_NAV_GRAPH = "homeNavGraph"

    const val HOME_ROOT = "homeRootRoute"
    const val HOME = "home"
    const val MY_BOX = "myBox"
    const val SETTING = "setting"
    const val SETTING_ACCOUNT = "settingAccount"
    const val WITHDRAWAL = "withdrawal"
}