package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.home.nickname.SettingNicknameScreen
import com.example.home.settingaccount.SettingAccountScreen
import com.example.home.settings.SettingsScreen
import com.example.home.withdrawal.WithdrawalScreen
import com.packy.core.animations.asPagingComposable
import com.packy.lib.ext.toEncoding
import okio.ByteString.Companion.encode

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

        asPagingComposable(
            route = SettingsRoute.SETTING_NICKNAME + "/{${SettingsArgs.NICKNAME}}" +
                    "/{${SettingsArgs.PROFILE_URL}}",
            arguments = listOf(
                navArgument(SettingsArgs.NICKNAME) {
                    type = NavType.StringType
                },
                navArgument(SettingsArgs.PROFILE_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            SettingNicknameScreen(
                navController = navController
            )
        }
    }
}

object SettingsRoute {
    const val SETTINGS_NAV_GRAPH = "settingsNavGraph"

    const val SETTING = "setting"
    const val SETTING_ACCOUNT = "settingAccount"
    const val WITHDRAWAL = "withdrawal"
    const val SETTING_NICKNAME = "settingNickname"

    fun getSettingNicknameRoute(
        nickname: String,
        profileUrl: String
    ) = "$SETTING_NICKNAME/$nickname/${profileUrl.toEncoding()}"
}

object SettingsArgs {
    const val NICKNAME = "nickname"
    const val PROFILE_URL = "profileUrl"
}