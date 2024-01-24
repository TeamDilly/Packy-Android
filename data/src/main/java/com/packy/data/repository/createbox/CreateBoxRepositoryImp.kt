package com.packy.data.repository.createbox

import com.packy.data.local.AccountPrefManager
import com.packy.domain.repository.createbox.CreateBoxRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateBoxRepositoryImp @Inject  constructor(
    private val prefManager: AccountPrefManager
): CreateBoxRepository {
    override suspend fun shouldShowBoxMotion(): Flow<Boolean> = prefManager.shouldShowBoxMotion.getData()

    override suspend fun shownShowBoxMotion() {
        prefManager.shouldShowBoxMotion.putData(false)
    }

    override suspend fun shouldShowBoxTutorial(): Flow<Boolean> = prefManager.shouldShowBoxTutorial.getData()

    override suspend fun shownShowBoxTutorial() {
        prefManager.shouldShowBoxTutorial.putData(false)
    }

}