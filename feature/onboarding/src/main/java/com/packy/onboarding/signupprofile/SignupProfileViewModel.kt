package com.packy.onboarding.signupprofile

import com.packy.mvi.base.MviViewModel
import com.packy.feature.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupProfileViewModel @Inject constructor() :
    MviViewModel<SignupProfileIntent, SignupProfileState, SignupProfileEffect>() {
    override fun createInitialState() = SignupProfileState(
        R.drawable.packy_logo
    )

    override fun handleIntent() {

    }
}