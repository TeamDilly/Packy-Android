package com.packy.onboarding.termsagreement

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface TermsAgreementIntent : MviIntent {
    data object OnAllTermsAgreementClick : MviIntent
    data object OnTermsOfServiceClick : MviIntent
    data object OnPersonalInformation : MviIntent
    data object OnNotification : MviIntent
}

data class TermsAgreementState(
    val enabledTermsOfService: Boolean,
    val enabledPersonalInformation: Boolean,
    val enabledNotification: Boolean
) : UiState

sealed interface TermsAgreementEffect : SideEffect

