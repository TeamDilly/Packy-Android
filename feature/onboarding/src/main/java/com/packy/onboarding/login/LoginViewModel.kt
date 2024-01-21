package com.packy.onboarding.login

import androidx.lifecycle.viewModelScope
import com.packy.common.authenticator.KakaoAuth
import com.packy.common.authenticator.KakaoLoginController
import com.packy.domain.model.auth.SignIn
import com.packy.domain.usecase.auth.SignInUseCase
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginController: KakaoLoginController,
    private val signInUseCase: SignInUseCase
) : MviViewModel<LoginIntent, LoginState, LoginEffect>() {

    override fun createInitialState(): LoginState = LoginState(
        isLoading = false,
        loginState = LogindState.Waiting
    )

    override fun handleIntent() {
        subscribeIntent<LoginIntent.OnKakaoLoginButtonClick> {
            kakoLogin()
        }
    }

    private fun kakoLogin() {
        kakaoLoginController.login { kakaoAuth ->
            when (kakaoAuth) {
                is KakaoAuth.KakaoLoginFail -> sendEffect(LoginEffect.KakaoLoginFail)
                is KakaoAuth.KakaoLoginSuccess -> {
                    viewModelScope.launch {
                        signInUseCase.signIn(kakaoAuth.token)
                            .collect {
                                signInController(it)
                            }
                    }
                }
            }
        }
    }

    private fun signInController(it: Resource<SignIn>) {
        when (it) {
            is Resource.Loading -> Unit
            is Resource.ApiError -> Unit
            is Resource.NetworkError -> Unit
            is Resource.NullResult -> Unit
            is Resource.Success -> {
                when (it.data.status) {
                    SignIn.AuthStatus.REGISTERED.name -> {
                        sendEffect(LoginEffect.KakaoLoginSuccess)
                    }

                    else -> sendEffect(LoginEffect.KakaoLoginSuccessNotUser)
                }
            }
        }
    }
}