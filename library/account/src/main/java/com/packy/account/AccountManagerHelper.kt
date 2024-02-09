package com.packy.account

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.os.Bundle
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class AccountManagerHelper(
    private val accountManager: AccountManager,
    private val authKey: AuthenticatorKey
) {
    /**
     * Token 삭제.
     */
    fun removeAuthToken() {
        val account = getAccount() ?: return
        accountManager.removeAccountExplicitly(account)
    }

    /**
     * @return accountType 의로 찾은 첫번째 계정을 반환 한다.
     */
    fun getAccount(): Account? {
        val accounts = accountManager.getAccountsByType(authKey.accountType)
        if (accounts.isEmpty()) {
            return null
        }
        return accounts.firstOrNull { it.type == authKey.accountType }
    }

    fun getAutToken(): String? {
        val account = getAccount() ?: return null
        return accountManager.peekAuthToken(account, authKey.authTokenType)
    }

    fun getRefreshToken(): String? {
        val account = getAccount() ?: return null
        return accountManager.getUserData(account, authKey.refreshToken)
    }

    fun getNickName(): String? {
        val account = getAccount() ?: return null
        return accountManager.getUserData(account, authKey.nickName)
    }

    /**
     * @return authToken을 찾아서 반환 한다.
     */
    suspend fun asyncGetAuthToken(): String? = withContext(Dispatchers.IO) {
        val account = getAccount() ?: return@withContext null
        val resultBundle = accountManager.callAsync<Bundle> { callback ->
            accountManager.getAuthToken(account, authKey.authTokenType, null, false, callback, null)
        }
        return@withContext resultBundle.getString(AccountManager.KEY_AUTHTOKEN)
    }

    /**
     * @param email Account email.
     * @param password Account password 필수 적이지 않음.
     * @param token 저장할 토큰.
     */
    fun setAuthToken(email: String, password: String? = null, token: String, refreshToken: String? = null, nickname: String? = null) {
        val accounts = accountManager.getAccountsByType(authKey.accountType)
        if (accounts.isEmpty()) {
            val account = Account(email, authKey.accountType)
            accountManager.addAccountExplicitly(account, password, null)
            accountManager.setAuthToken(account, authKey.authTokenType, token)
            accountManager.setUserData(account, authKey.refreshToken, refreshToken)
            accountManager.setUserData(account, authKey.nickName, nickname)
        } else {
            val account = accounts.firstOrNull { it.type == authKey.accountType }
            accountManager.setAuthToken(account, authKey.authTokenType, token)
            accountManager.setUserData(account, authKey.refreshToken, refreshToken)
            accountManager.setUserData(account, authKey.nickName, nickname)
        }
    }

    fun setRefreshToken( token: String, refreshToken: String) {
        val account = getAccount() ?: return
        accountManager.setAuthToken(account, authKey.authTokenType, token)
        accountManager.setUserData(account, authKey.refreshToken, refreshToken)
    }

    private suspend inline fun <R> AccountManager.callAsync(crossinline operation: AccountManager?.(CoroutineAccountManagerCallback<R>) -> Unit): R {
        return suspendCancellableCoroutine { cont ->
            operation(CoroutineAccountManagerCallback(cont))
        }
    }

    class CoroutineAccountManagerCallback<T>(private val cont: CancellableContinuation<T>) :
        AccountManagerCallback<T> {
        @OptIn(InternalCoroutinesApi::class)
        override fun run(future: AccountManagerFuture<T>) {
            try {
                if (future.isCancelled) {
                    cont.cancel()
                } else {
                    cont.resume(future.result)
                }
            } catch (e: Exception) {
                if (e is CancellationException) {
                    cont.cancel(e)
                } else {
                    cont.tryResumeWithException(e)
                }
            }
        }
    }
}