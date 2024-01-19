package com.packy.onboarding.login

import androidx.lifecycle.viewModelScope
import com.packy.common.authenticator.KakaoAuth
import com.packy.common.authenticator.KakaoLoginController
import com.packy.domain.usecase.auth.SignInUseCase
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginController: KakaoLoginController,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : MviViewModel<LoginIntent, LoginState, LoginEffect>() {

    override fun createInitialState(): LoginState = LoginState(
        isLoading = false,
        loginState = LogindState.Waiting
    )

    override fun handleIntent() {
        subscribeIntent<LoginIntent.OnKakaoLoginButtonClick> {
            kakaoLoginController.login { kakaoAuth ->
                when (kakaoAuth) {
                    is KakaoAuth.KakaoLoginFail -> sendEffect(LoginEffect.KakaoLoginFail)
                    is KakaoAuth.KakaoLoginSuccess -> {
                        viewModelScope.launch {
                            signInUseCase.signIn(kakaoAuth.token)
                                .collect{

                                }
                        }
                    }
                }
            }
        }
    }

    private fun signUp(kakaoAuth: KakaoAuth.KakaoLoginSuccess) {
        viewModelScope.launch {
            val signUp = signUpUseCase.getUserSignUpInfo().first()
            signUpUseCase.setUserSignUpInfo(
                signUp.copy(
                    token = kakaoAuth.token,
                    provider = "kakao"
                )
            )
            sendEffect(LoginEffect.KakaoLoginSuccess)
        }
    }


}