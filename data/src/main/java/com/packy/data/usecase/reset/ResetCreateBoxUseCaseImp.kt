package com.packy.data.usecase.reset

import com.packy.data.local.AccountPrefManager
import com.packy.domain.usecase.reset.ResetCreateBoxUseCase
import javax.inject.Inject

class ResetCreateBoxUseCaseImp @Inject constructor(
    private val accountPrefManager: AccountPrefManager
): ResetCreateBoxUseCase {
    override suspend fun resetCreateBox() {
        accountPrefManager.createBox.reset()
        accountPrefManager.shouldShowBoxMotion.reset()
        accountPrefManager.shouldShowBoxTutorial.reset()
    }
}