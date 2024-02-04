package com.packy.common.authenticator

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginController @Inject constructor() {
    private fun kakaoLoginCallback(
        token: OAuthToken?,
        throwable: Throwable?,
        callback: (KakaoAuth) -> Unit
    ) {
        if (throwable != null) {
            callback(KakaoAuth.KakaoLoginFail(throwable))
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    callback(KakaoAuth.KakaoLoginFail(error))
                } else if (user != null) {
                    callback(
                        KakaoAuth.KakaoLoginSuccess(
                            token.accessToken,
                            user.kakaoAccount?.profile?.nickname
                        )
                    )
                }
            }
        }
    }

    fun login(
        context: Context,
        callback: (KakaoAuth) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                        kakaoLoginCallback(token, error, callback)
                    }
                } else if (token != null) {
                    kakaoLoginCallback(token, null, callback)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                kakaoLoginCallback(token, error, callback)
            }
        }
    }
}