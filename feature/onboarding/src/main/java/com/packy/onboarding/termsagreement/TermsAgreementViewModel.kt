package com.packy.onboarding.termsagreement

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsAgreementViewModel @Inject constructor() :
    MviViewModel<TermsAgreementIntent, TermsAgreementState, TermsAgreementEffect>() {
    override fun createInitialState() = TermsAgreementState(
        enabledTermsOfService = false,
        enabledPersonalInformation = false,
        enabledNotification = false
    )

    override fun handleIntent() {

    }
}