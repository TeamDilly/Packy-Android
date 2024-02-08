package com.packy.onboarding.signupprofile

import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.domain.usecase.profile.GetProfilesUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SignupProfileViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getProfilesUseCase: GetProfilesUseCase
) :
    MviViewModel<SignupProfileIntent, SignupProfileState, SignupProfileEffect>() {
    override fun createInitialState() = SignupProfileState(
        Profile.PROFILE1,
        listOf(
            Profile.PROFILE2,
            Profile.PROFILE3,
            Profile.PROFILE4,
            Profile.PROFILE5,
        )
    )

    override fun handleIntent() {
        subscribeIntent<SignupProfileIntent.OnBackClick> {
            sendEffect(SignupProfileEffect.MoveToBack)
        }
        subscribeIntent<SignupProfileIntent.OnSaveButtonClick> {
            val signUp = signUpUseCase.getUserSignUpInfo().first()
            signUpUseCase.setUserSignUpInfo(
                signUp.copy(
                    profileImg = currentState.profiles.indexOf(
                        currentState.selectedProfile
                    )
                )
            )
            sendEffect(SignupProfileEffect.NavTermsAgreementEffect)
        }
        subscribeStateIntent<SignupProfileIntent.OnChangeProfile> { state, intent ->
            if (state.selectedProfile != intent.newProfile) {
                sendEffect(SignupProfileEffect.ProfileChangeHapticEffect)
                state.copy(selectedProfile = intent.newProfile)
            } else {
                state
            }
        }
    }
}