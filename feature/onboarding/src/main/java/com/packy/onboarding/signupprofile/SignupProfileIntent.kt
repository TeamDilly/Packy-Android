package com.packy.onboarding.signupprofile

import androidx.annotation.DrawableRes
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SignupProfileIntent : MviIntent {
    data object OnBackClick : MviIntent
    data object OnChangeProfile : MviIntent
    data object OnSaveButtonClick : MviIntent
}

data class SignupProfileState(
    @DrawableRes val profile: Int
) : UiState

sealed interface SignupProfileEffect : SideEffect

