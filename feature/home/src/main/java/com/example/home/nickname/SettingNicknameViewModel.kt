package com.example.home.nickname

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.home.navigation.SettingsArgs
import com.packy.domain.usecase.profile.UpdateNickNameUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingNicknameViewModel @Inject constructor(
    private val updateNickNameUseCase: UpdateNickNameUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<SettingNicknameIntent, SettingNicknameState, SettingNicknameEffect>() {
    override fun createInitialState(): SettingNicknameState = SettingNicknameState(

    )

    override fun handleIntent() {
        subscribeIntent<SettingNicknameIntent.OnBackClick> { sendEffect(SettingNicknameEffect.MoveToBack) }
        subscribeIntent<SettingNicknameIntent.OnSettingProfileClick> { sendEffect(SettingNicknameEffect.MoveToSettingProfile(currentState.profileImage)) }
        subscribeIntent<SettingNicknameIntent.OnSaveClick> {
            currentState.currentNickName?.let { nickname ->
                updateNickNameUseCase.updateNickName(nickname)
                    .filterSuccess()
                    .unwrapResource()
                    .collect{
                        setState {
                            it.copy(
                                prevNickname = currentState.currentNickName
                            )
                        }
                        sendEffect(SettingNicknameEffect.MoveToBack)
                    }
            }
        }
        subscribeStateIntent<SettingNicknameIntent.ChangeNickName>{ state, intent ->
            state.copy(
                currentNickName = intent.nickname
            )
        }
        subscribeStateIntent<SettingNicknameIntent.ChangeProfileImage>{ state, intent ->
            state.copy(
                profileImage = intent.profileImage
            )
        }
    }
    init {
        savedStateHandle.getStateFlow(key = SettingsArgs.NICKNAME, initialValue = "").let{ nickname ->
            viewModelScope.launch {
                nickname.collect { nickname ->
                    setState {
                        it.copy(
                            prevNickname = nickname,
                            currentNickName = nickname
                        )
                    }
                }
            }
        }
        savedStateHandle.getStateFlow(key = SettingsArgs.PROFILE_URL, initialValue = "").let{ profileUrl ->
            viewModelScope.launch {
                profileUrl.collect { profileUrl ->
                    setState {
                        it.copy(
                            profileImage = profileUrl
                        )
                    }
                }
            }
        }
    }
}