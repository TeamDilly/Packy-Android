package com.packy.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.os.Bundle

class Authenticator(
    private val context: Context
) : AbstractAccountAuthenticator(context) {
    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle = Bundle()

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        features: Array<out String>?,
        bundle: Bundle?
    ): Bundle = Bundle()

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle = Bundle()

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle = Bundle()

    override fun getAuthTokenLabel(authTokenType: String?): String = ""

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle = Bundle()

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle = Bundle()
}