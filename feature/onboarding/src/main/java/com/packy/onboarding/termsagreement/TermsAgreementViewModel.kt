package com.packy.onboarding.termsagreement

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsAgreementViewModel @Inject constructor() :
    MviViewModel<TermsAgreementIntent, TermsAgreementState, TermsAgreementEffect>() {
    override fun createInitialState() = TermsAgreementState(
        isAllAllow = false,
        isRequiredAllow = false,
        enabledTermsOfService = false,
        enabledPersonalInformation = false,
        enabledNotification = false
    )

    override fun handleIntent() {
        subscribeStateIntent<TermsAgreementIntent.OnAllTermsAgreementClick> { state, _ ->
            state.copy(
                isAllAllow = !state.isAllAllow,
                isRequiredAllow = !state.isAllAllow,
                enabledTermsOfService = !state.isAllAllow,
                enabledPersonalInformation = !state.isAllAllow,
                enabledNotification = !state.isAllAllow,
            )
        }
        subscribeStateIntent<TermsAgreementIntent.OnTermsOfServiceClick> { state, _ ->
            state.copy(
                isAllAllow = state.enabledAllAllow(enabledTermsOfService = !state.enabledTermsOfService),
                isRequiredAllow = state.enabledRequired(enabledTermsOfService = !state.enabledTermsOfService),
                enabledTermsOfService = !state.enabledTermsOfService,
            )
        }
        subscribeStateIntent<TermsAgreementIntent.OnPersonalInformation> { state, _ ->
            state.copy(
                isAllAllow = state.enabledAllAllow(enabledPersonalInformation = !state.enabledPersonalInformation),
                isRequiredAllow = state.enabledRequired(enabledPersonalInformation = !state.enabledPersonalInformation),
                enabledPersonalInformation = !state.enabledPersonalInformation,
            )
        }
        subscribeStateIntent<TermsAgreementIntent.OnNotification> { state, _ ->
            state.copy(
                isAllAllow = state.enabledAllAllow(enabledNotification = !state.enabledNotification),
                enabledNotification = !state.enabledNotification,
            )
        }
    }
}