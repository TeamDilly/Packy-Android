package com.packy.di.authenticator

import com.packy.account.AuthenticatorKey

object ExampleAuthenticatorKey: AuthenticatorKey {
    override val accountType: String
        get() = "Packy"
    override val authTokenType: String
        get() = "PackyUser"
}
