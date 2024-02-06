package com.example.home.settings

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    MviViewModel<SettingsIntent, SettingsState, SettingsEffect>() {
    override fun createInitialState(): SettingsState = SettingsState(
        profileImage = null,
        profileName = null,
        settings = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<SettingsIntent.OnWebSettingClick> { sendEffect(SettingsEffect.MoveToWeb(it.url)) }
        subscribeIntent<SettingsIntent.OnLogoutClick> { sendEffect(SettingsEffect.Logout) }
        subscribeIntent<SettingsIntent.OnAccountManageClick> { sendEffect(SettingsEffect.MoveToAccountManage) }
    }
}