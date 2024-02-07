package com.packy.data.usecase.auth

import com.packy.data.local.AccountPrefManager
import com.packy.data.local.GlobalPrefManager
import com.packy.domain.usecase.auth.LogoutUseCase
import javax.inject.Inject

class LogoutUseCaseImp @Inject constructor(
    private val accountPrefManager: AccountPrefManager,
    private val globalPrefManager: GlobalPrefManager
) : LogoutUseCase {
    override suspend fun logout() {
        accountPrefManager.clearAll()
        globalPrefManager.clearAll()
    }

}