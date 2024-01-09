package com.packy.onboarding.login

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : MviViewModel<LoginIntent, LoginState, LoginEffect>() {
    override fun createInitialState(): LoginState = LoginState(
        isLoading = false,
        loginState = LogindState.Waiting
    )

    override fun handleIntent() {

    }
}