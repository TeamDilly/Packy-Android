package com.example.home.settings

import com.packy.domain.model.settings.SettingItem
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SettingsIntent : MviIntent {

    data object OnBackClick : SettingsIntent
    data class OnWebSettingClick(val url: String) : SettingsIntent
    data object OnAccountManageClick : SettingsIntent
    data object OnLogoutClick : SettingsIntent

    data object OnModifyProfileClick: SettingsIntent
}

data class SettingsState(
    val profileImage: String?,
    val profileName: String?,
    val settings: List<SettingItem>
) : UiState

sealed interface SettingsEffect : SideEffect {
    data object Logout : SettingsEffect
    data class MoveToWeb(val url: String) : SettingsEffect

    data object MoveToAccountManage : SettingsEffect
    data object MoveToBack : SettingsEffect

    data class MoveToModifyProfile(
        val profileName: String?,
        val profileImage: String?,
    ): SettingsEffect
}