package com.packy.onboarding.signupnickname

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface SignupNickNameIntent : MviIntent {
    data object OnSaveButtonClick : SignupNickNameIntent
    data class OnChangeInputNickName(
        val inputNickName: String?
    ) : SignupNickNameIntent
}

data class SignupNickNameState(
    val inputNickName: String?,
    val isInputNickNAmeFail: Boolean
) : UiState

sealed interface SignupNickNameEffect: SideEffect