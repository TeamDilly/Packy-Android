package com.example.home.nickname

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.home.navigation.SettingsArgs
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingNicknameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<SettingNicknameIntent, SettingNicknameState, SettingNicknameEffect>() {
    override fun createInitialState(): SettingNicknameState = SettingNicknameState(

    )

    override fun handleIntent() {
        subscribeIntent<SettingNicknameIntent.OnBackClick> { sendEffect(SettingNicknameEffect.MoveToBack) }
        subscribeIntent<SettingNicknameIntent.OnSaveClick> {  }
        subscribeStateIntent<SettingNicknameIntent.ChangeNickName>{ state, intent ->
            state.copy(
                currentNickName = intent.nickname
            )
        }
    }

    init {
        savedStateHandle.get<String>(SettingsArgs.NICKNAME)?.let{ nickname ->
            viewModelScope.launch {
                setState {
                    it.copy(
                        prevNickname = nickname,
                        currentNickName = nickname
                    )
                }
            }
        }
        savedStateHandle.get<String>(SettingsArgs.PROFILE_URL)?.let{ profileUrl ->
            viewModelScope.launch {
                setState {
                    it.copy(
                        profileImage = profileUrl,
                    )
                }
            }
        }
    }
}