package com.example.home.nickname

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SettingNicknameIntent : MviIntent {
    data class ChangeNickName(
        val nickname: String
    ) : SettingNicknameIntent

    data class ChangeProfileImage(
        val profileImage: String
    ) : SettingNicknameIntent

    data object OnBackClick : SettingNicknameIntent
    data object OnSaveClick : SettingNicknameIntent
    data object OnSettingProfileClick : SettingNicknameIntent
}

data class SettingNicknameState(
    val prevNickname: String? = null,
    val currentNickName: String? = null,
    val profileImage: String? = null
) : UiState

sealed interface SettingNicknameEffect : SideEffect {
    data object MoveToBack : SettingNicknameEffect
    data object SavedNewNickName : SettingNicknameEffect
    data object FailSaveNewNickName : SettingNicknameEffect

    data class MoveToSettingProfile(
        val profileImage: String? = null
    ) : SettingNicknameEffect
}