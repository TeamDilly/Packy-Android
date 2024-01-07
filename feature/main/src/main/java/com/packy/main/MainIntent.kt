package com.packy.main

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

class MainIntent {
    sealed interface MainMviIntent : MviIntent {
        data object Loading : MainMviIntent

        data object RemoveToken : MainMviIntent

        data class SetToken(
            val email: String,
            val token: String
        ) : MainMviIntent

        data class GetToken(
            val token: String
        ) : MainMviIntent
    }

    data class MainState(
        val isLoading: Boolean,
        val isSuccessSetToken: Boolean,
        val token: String? = null
    ) : UiState

    sealed class Effect : SideEffect {

    }
}