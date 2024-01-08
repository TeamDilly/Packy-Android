package com.packy.onboarding.login

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface LoginIntent : MviIntent {
    data object OnKakaoLoginButtonClick : LoginIntent
    data object KakaoLoginSuccess : LoginIntent
    data object KakaoLoginFail : LoginIntent
}

sealed interface LogindState {
    data object Waiting : LogindState
}

data class LoginState(
    val isLoading: Boolean,
    val loginState: LogindState
) : UiState

sealed interface LoginEffect : SideEffect {
    data object LoginKakao : LoginEffect
}