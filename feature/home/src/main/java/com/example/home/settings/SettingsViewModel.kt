package com.example.home.settings

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.home.GetMyProfileUseCase
import com.packy.domain.usecase.home.GetSettingUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingUseCase: GetSettingUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase
) :
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

    fun getSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            getSettingUseCase.getSettings().filterSuccess()
                .combine(getMyProfileUseCase.getMyProfile().filterSuccess()) { settings, profile ->
                    SettingsState(
                        profileImage = profile.data.imgUrl,
                        profileName = profile.data.nickname,
                        settings = settings.data
                    )
                }
                .collect {
                    setState(it)
                }
        }
    }

    fun logout(){
        //TODO
    }
}