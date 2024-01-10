package com.packy.onboarding.signupprofile

import androidx.annotation.DrawableRes
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SignupProfileIntent : MviIntent {
    data object OnBackClick : SignupProfileIntent
    data class OnChangeProfile(
        val newProfile: Profile,
    ) : SignupProfileIntent

    data object OnSaveButtonClick : SignupProfileIntent
}

data class SignupProfileState(
    val selectedProfile: Profile,
    val profiles: List<Profile>
) : UiState

enum class Profile(
    @DrawableRes val url: Int
) {
    PROFILE1(com.packy.feature.core.R.drawable.logo_black),
    PROFILE2(com.packy.feature.core.R.drawable.packy_logo),
    PROFILE3(com.packy.feature.core.R.drawable.logo_black),
    PROFILE4(com.packy.feature.core.R.drawable.packy_logo),
    PROFILE5(com.packy.feature.core.R.drawable.logo_black)
}

sealed interface SignupProfileEffect : SideEffect {
    data object NavTermsAgreementEffect : SignupProfileEffect
    data object ProfileChangeHapticEffect : SignupProfileEffect
}

