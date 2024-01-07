package com.packy.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.packy.account.AccountManagerHelper
import com.packy.domain.usecase.example.ExampleUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase,
    private val accountManagerHelper: AccountManagerHelper
) : MviViewModel<MainIntent.MainMviIntent, MainIntent.MainState, MainIntent.Effect>() {

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                exampleUseCase.getSamplePref()
                    .collect {
                        Log.d("LOGEE", "Result $it: ")
                    }
            }
            exampleUseCase.setSamplePref("World!")
            exampleUseCase.setSamplePref("Kim!")
        }
    }

    private fun removeToken() {
        accountManagerHelper.removeAuthToken()
    }

    private fun callApi(email: String, token: String) {
        viewModelScope.launch {
            exampleUseCase.getExample()
                .collect {
                    Log.d("LOGEE", "callApi: $it")
                }
        }
    }

    override fun createInitialState(): MainIntent.MainState = MainIntent.MainState(
        isLoading = false,
        isSuccessSetToken = false,
    )

    override fun handleIntent() {
        subscribeIntent<MainIntent.MainMviIntent.SetToken> {
            callApi(it.email, it.token)
        }
        subscribeIntent<MainIntent.MainMviIntent.RemoveToken> {
            removeToken()
        }
        subscribeStateIntent<MainIntent.MainMviIntent.Loading> { state, _ ->
            state.copy(isLoading = false)
        }
        subscribeStateIntent<MainIntent.MainMviIntent.GetToken> { state, intent ->
            state.copy(isLoading = true)
        }
    }
}