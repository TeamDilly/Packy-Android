package com.packy.onboarding.signupprofile

import android.view.View
import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.domain.usecase.profile.GetProfilesUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupProfileViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getProfilesUseCase: GetProfilesUseCase
) :
    MviViewModel<SignupProfileIntent, SignupProfileState, SignupProfileEffect>() {
    override fun createInitialState() = SignupProfileState(
        selectedProfile = null,
        profiles = emptyList()
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

    fun initProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            getProfilesUseCase.getProfile()
                .filterSuccess()
                .unwrapResource()
                .collect { prfiles ->
                    setState { state ->
                        state.copy(
                            selectedProfile = prfiles.firstOrNull(),
                            profiles = prfiles
                        )
                    }
                }

        }
    }
}