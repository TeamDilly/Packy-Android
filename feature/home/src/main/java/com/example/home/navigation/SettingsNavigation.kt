package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.home.settingaccount.SettingAccountScreen
import com.example.home.settings.SettingsScreen
import com.example.home.withdrawal.WithdrawalScreen
import com.packy.core.animations.asPagingComposable

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    logout: () -> Unit
) {
    navigation(
        startDestination = SettingsRoute.SETTING,
        route = SettingsRoute.SETTINGS_NAV_GRAPH,
    ) {

        asPagingComposable(
            route = SettingsRoute.SETTING
        ) {
            SettingsScreen(
                navController = navController,
                logout = logout
            )
        }

        asPagingComposable(
            route = SettingsRoute.SETTING_ACCOUNT
        ) {
            SettingAccountScreen(
                navController = navController
            )
        }

        asPagingComposable(
            route = SettingsRoute.WITHDRAWAL
        ) {
            WithdrawalScreen(
                navController = navController,
                logout = logout
            )
        }
    }
}

object SettingsRoute {
    const val SETTINGS_NAV_GRAPH = "settingsNavGraph"

    const val SETTING = "setting"
    const val SETTING_ACCOUNT = "settingAccount"
    const val WITHDRAWAL = "withdrawal"
}