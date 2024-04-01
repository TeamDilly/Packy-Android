package com.packy.di.authenticator

import com.packy.account.AuthenticatorKey

object PackyAuthenticatorKey: AuthenticatorKey {
    override val accountType: String
        get() = "com.Packy"
    override val authTokenType: String
        get() = "PackyUser"

    override val refreshToken: String
        get() = "refreshToken"
    override val nickName: String
        get() = "PackyNickName"
    override val memberId: String
        get() = "PackyMemberId"
}
