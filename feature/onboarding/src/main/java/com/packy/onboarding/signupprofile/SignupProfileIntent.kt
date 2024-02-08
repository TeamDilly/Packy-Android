package com.packy.onboarding.signupprofile

import androidx.annotation.DrawableRes
import com.packy.domain.model.profile.ProfileDesign
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SignupProfileIntent : MviIntent {
    data object OnBackClick : SignupProfileIntent
    data class OnChangeProfile(
        val newProfile: ProfileDesign,
    ) : SignupProfileIntent

    data object OnSaveButtonClick : SignupProfileIntent
}

data class SignupProfileState(
    val selectedProfile: ProfileDesign?,
    val profiles: List<ProfileDesign>
) : UiState

sealed interface SignupProfileEffect : SideEffect {
    data object MoveToBack: SignupProfileEffect
    data object NavTermsAgreementEffect : SignupProfileEffect
    data object ProfileChangeHapticEffect : SignupProfileEffect
}

