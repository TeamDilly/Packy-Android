package com.example.home.withdrawal

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor() :
    MviViewModel<WithdrawalIntent, WithdrawalState, WithdrawalEffect>() {
    override fun createInitialState(): WithdrawalState = WithdrawalState(
        isLoading = false,
        withdrawn = false
    )

    override fun handleIntent() {

    }
}