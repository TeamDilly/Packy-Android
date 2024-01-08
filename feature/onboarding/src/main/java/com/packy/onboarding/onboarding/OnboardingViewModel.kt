package com.packy.onboarding.onboarding

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() :
    MviViewModel<OnboardingIntent, OnboardingState, OnboardingEffect>() {
    override fun createInitialState(): OnboardingState = OnboardingState(
        isLoading = true
    )

    override fun handleIntent() {
        subscribeIntent<OnboardingIntent.OnStartButtonClick> {
            sendEffect(OnboardingEffect.GoToLoginScreenEffect)
        }
    }
}