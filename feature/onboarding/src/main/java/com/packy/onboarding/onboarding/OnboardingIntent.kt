package com.packy.onboarding.onboarding

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface OnboardingIntent : MviIntent {
    data object OnStartButtonClick : OnboardingIntent
}

data class OnboardingState(
    val isLoading: Boolean,
) : UiState

sealed interface OnboardingEffect : SideEffect{
    data object GoToLoginScreenEffect: OnboardingEffect
}
