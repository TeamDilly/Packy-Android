package com.packy.onboarding.termsagreement

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface TermsAgreementIntent : MviIntent {
    data object OnAllTermsAgreementClick : TermsAgreementIntent
    data object OnTermsOfServiceClick : TermsAgreementIntent
    data object OnPersonalInformation : TermsAgreementIntent
    data object OnNotification : TermsAgreementIntent
    data object OnBackClick : TermsAgreementIntent
    data object OnSaveButtonClick : TermsAgreementIntent
}

data class TermsAgreementState(
    val isAllAllow: Boolean,
    val isRequiredAllow: Boolean,
    val enabledTermsOfService: Boolean,
    val enabledPersonalInformation: Boolean,
    val enabledNotification: Boolean,
    val signUpFail: Boolean
) : UiState {
    fun enabledAllAllow(
        enabledTermsOfService: Boolean = this.enabledTermsOfService,
        enabledPersonalInformation: Boolean = this.enabledPersonalInformation,
        enabledNotification: Boolean = this.enabledNotification
    ) =
        enabledTermsOfService && enabledPersonalInformation && enabledNotification

    fun enabledRequired(
        enabledTermsOfService: Boolean = this.enabledTermsOfService,
        enabledPersonalInformation: Boolean = this.enabledPersonalInformation,
    ) = enabledTermsOfService && enabledPersonalInformation
}

sealed interface TermsAgreementEffect : SideEffect{
    data object MoveToBack : TermsAgreementEffect
    data object OnSuccessSignUp : TermsAgreementEffect
}

