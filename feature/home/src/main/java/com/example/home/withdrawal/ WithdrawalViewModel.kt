package com.example.home.withdrawal

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor() :
    MviViewModel<WithdrawalIntent, WithdrawalState, WithdrawalEffect>() {
    override fun createInitialState(): WithdrawalState = WithdrawalState(
        isLoading = false,
        withdrawn = false,
        showWithdrawalDialog = false
    )

    override fun handleIntent() {
        subscribeIntent<WithdrawalIntent.OnBackClick> { sendEffect(WithdrawalEffect.MoveToBack) }
        subscribeStateIntent<WithdrawalIntent.OnShowWithdrawalDialogClick> { state, _ -> state.copy(showWithdrawalDialog = true) }
        subscribeStateIntent<WithdrawalIntent.OnWithdrawalClick> { state, _ -> state.copy(showWithdrawalDialog = false)  }
        subscribeStateIntent<WithdrawalIntent.OnCloseWithdrawalDialogClick>{ state, _ -> state.copy(showWithdrawalDialog = false) }
    }
}