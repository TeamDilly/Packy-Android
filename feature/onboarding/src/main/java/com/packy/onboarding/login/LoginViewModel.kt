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
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : MviViewModel<LoginIntent, LoginState, LoginEffect>() {

    override fun createInitialState(): LoginState = LoginState(
        isLoading = false,
        loginState = LogindState.Waiting
    )

    override fun handleIntent() {
        subscribeIntent<LoginIntent.OnKakaoLoginButtonClick> {
            sendEffect(LoginEffect.KakaoLogin)
        }
    }

    fun kakoLogin(kakaoAuth: KakaoAuth) {
        when (kakaoAuth) {
            is KakaoAuth.KakaoLoginFail -> sendEffect(LoginEffect.KakaoLoginFail)
            is KakaoAuth.KakaoLoginSuccess -> {
                viewModelScope.launch {
                    signInUseCase.signIn(
                        token = kakaoAuth.token,
                        nickname = kakaoAuth.nickname
                    )
                        .collect { resource ->
                            signInController(
                                token = kakaoAuth.token,
                                resource = resource,
                                nikname = kakaoAuth.nickname
                            )
                        }
                }
            }
        }
    }

    private fun signInController(
        token: String,
        nikname: String?,
        resource: Resource<SignIn>
    ) {
        when (resource) {
            is Resource.Loading -> Unit
            is Resource.ApiError -> Unit
            is Resource.NetworkError -> Unit
            is Resource.NullResult -> Unit
            is Resource.Success -> {
                when (resource.data.status) {
                    SignIn.AuthStatus.REGISTERED.name -> {
                        viewModelScope.launch {
                            sendEffect(LoginEffect.KakaoLoginSuccess)
                        }
                    }

                    else -> {
                        viewModelScope.launch {
                            val signUp = signUpUseCase.getUserSignUpInfo().first().copy(
                                token = token
                            )
                            signUpUseCase.setUserSignUpInfo(
                                signUp
                            )
                            sendEffect(LoginEffect.KakaoLoginSuccessNotUser(nikname))
                        }
                    }
                }
            }
        }
    }
}