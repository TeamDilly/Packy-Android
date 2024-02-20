package com.example.home.profileimage

import com.packy.domain.model.profile.ProfileDesign
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SettingsProfileImageIntent : MviIntent {
    data object OnBackClick: SettingsProfileImageIntent
    data class OnChangeProfile(
        val newProfileImageUrl: String
    ): SettingsProfileImageIntent
    data object OnSaveClick: SettingsProfileImageIntent
}

data class SettingsProfileImageState(
    val prevProfileImageUrl: String? = null,
    val currentProfileImageUrl: String? = null,
    val profiles: List<ProfileDesign> = emptyList()
) : UiState

sealed interface SettingsProfileImageEffect : SideEffect {
    data object MoveToBack: SettingsProfileImageEffect
}