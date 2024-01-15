package com.packy.common.authenticator

sealed interface KakaoAuth {
    data class KakaoLoginSuccess(
        val token: String,
        val nickname: String?
    ) : KakaoAuth

    data class KakaoLoginFail(
        val error: Throwable
    ): KakaoAuth
}


