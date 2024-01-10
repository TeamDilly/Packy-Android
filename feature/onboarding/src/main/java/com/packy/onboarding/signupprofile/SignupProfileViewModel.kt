package com.packy.onboarding.signupprofile

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupProfileViewModel @Inject constructor() :
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
        subscribeIntent<SignupProfileIntent.OnSaveButtonClick> {
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