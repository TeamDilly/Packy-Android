package com.example.home.withdrawal

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.auth.WithdrawUseCase
import com.packy.lib.utils.Resource
import com.packy.lib.utils.filterLoading
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val withdrawUseCase: WithdrawUseCase
) :
    MviViewModel<WithdrawalIntent, WithdrawalState, WithdrawalEffect>() {
    override fun createInitialState(): WithdrawalState = WithdrawalState(
        isLoading = false,
        withdrawn = false,
        showWithdrawalDialog = false
    )

    override fun handleIntent() {
        subscribeIntent<WithdrawalIntent.OnBackClick> { sendEffect(WithdrawalEffect.MoveToBack) }
        subscribeStateIntent<WithdrawalIntent.OnShowWithdrawalDialogClick> { state, _ -> state.copy(showWithdrawalDialog = true) }
        subscribeStateIntent<WithdrawalIntent.OnWithdrawalClick> { state, _ ->
            withContext(Dispatchers.IO) {
                val withdraw = withdrawUseCase.withdraw().filterLoading().first()

                when (withdraw) {
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }

                    is Resource.ApiError,
                    is Resource.NetworkError,
                    is Resource.NullResult -> {
                        state.copy(showWithdrawalDialog = false)
                    }

                    is Resource.Success -> {
                        sendEffect(WithdrawalEffect.Logout)
                        state.copy(showWithdrawalDialog = false)
                    }
                }

            }
        }
        subscribeStateIntent<WithdrawalIntent.OnCloseWithdrawalDialogClick> { state, _ -> state.copy(showWithdrawalDialog = false) }
    }
}