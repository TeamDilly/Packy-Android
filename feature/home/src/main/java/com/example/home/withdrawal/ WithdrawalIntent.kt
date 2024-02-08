package com.example.home.withdrawal

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface WithdrawalIntent : MviIntent {
    data object OnBackClick: WithdrawalIntent
    data object OnWithdrawalClick:  WithdrawalIntent
}

data class WithdrawalState(
    val isLoading: Boolean,
    val withdrawn: Boolean
) : UiState

sealed interface WithdrawalEffect : SideEffect {
    data object MoveToBack: WithdrawalEffect
}