package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.home.home.HomeScreen
import com.example.home.mybox.MyBoxScreen
import com.example.home.navigation.HomeRoute.HOME
import com.example.home.navigation.HomeRoute.MY_BOX
import com.example.home.settingaccount.SettingAccountScreen
import com.example.home.settings.SettingsScreen
import com.example.home.withdrawal.WithdrawalScreen
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    logout: () -> Unit
) {
    navigation(
        startDestination = HomeRoute.HOME,
        route = HomeRoute.HOME_NAV_GRAPH,
    ) {
        asRootComposable(
            route = HOME
        ) {
            HomeScreen(
                navController = navController,
                moveToCreateBox = moveToCreateBox,
                moveToBoxDetail = moveToBoxDetail
            )
        }

        asPagingComposable(
            route = MY_BOX
        ) {
            MyBoxScreen(
                navController = navController,
                moveToCreateBox = moveToCreateBox,
                moveToBoxDetail = moveToBoxDetail
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
            WithdrawalScreen(navController = navController)
        }
    }
}

object HomeRoute {
    const val HOME_NAV_GRAPH = "homeNavGraph"

    const val HOME = "home"
    const val MY_BOX = "myBox"
    const val SETTING = "setting"
    const val SETTING_ACCOUNT = "settingAccount"
    const val WITHDRAWAL = "withdrawal"
}