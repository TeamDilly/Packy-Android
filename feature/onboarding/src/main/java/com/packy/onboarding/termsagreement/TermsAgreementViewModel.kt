package com.packy.onboarding.termsagreement

import android.util.Log
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class TermsAgreementViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) :
    MviViewModel<TermsAgreementIntent, TermsAgreementState, TermsAgreementEffect>() {
    override fun createInitialState() = TermsAgreementState(
        isAllAllow = false,
        isRequiredAllow = false,
        enabledTermsOfService = false,
        enabledPersonalInformation = false,
        enabledNotification = false,
        signUpFail = false
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
        subscribeIntent<TermsAgreementIntent.OnBackClick> {
            sendEffect(TermsAgreementEffect.MoveToBack)
        }
        subscribeIntent<TermsAgreementIntent.OnSaveButtonClick> {
            signUpUseCase.setUserSignUpInfo(
                signUpUseCase.getUserSignUpInfo().first().copy(
                    serviceAllow = currentState.enabledTermsOfService,
                    personalAllow = currentState.enabledPersonalInformation,
                    marketingAgreement = currentState.enabledNotification
                )
            )
            signUpUseCase
                .signUp(signUpUseCase.getUserSignUpInfo().first())
                .collect { resource ->
                    when (resource) {
                        is Resource.ApiError,
                        is Resource.Loading,
                        is Resource.NetworkError,
                        is Resource.NullResult -> setState {
                            currentState.copy(signUpFail = true)
                        }

                        is Resource.Success -> sendEffect(TermsAgreementEffect.OnSuccessSignUp)
                    }
                }
        }
    }
}