package com.packy.onboarding.signupprofile

import androidx.annotation.DrawableRes
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SignupProfileIntent : MviIntent {
    data object OnBackClick : SignupProfileIntent
    data object OnChangeProfile : SignupProfileIntent
    data object OnSaveButtonClick : SignupProfileIntent
}

data class SignupProfileState(
    @DrawableRes val profile: Int
) : UiState

sealed interface SignupProfileEffect : SideEffect {
    data object NavTermsAgreementEffect : SignupProfileEffect
}

