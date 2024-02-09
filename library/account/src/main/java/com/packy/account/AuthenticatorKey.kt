package com.packy.account

interface AuthenticatorKey {
    val accountType: String
    val authTokenType: String
    val refreshToken: String
    val nickName: String
}