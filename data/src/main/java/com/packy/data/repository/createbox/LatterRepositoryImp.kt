package com.packy.data.repository.createbox

import com.packy.data.local.AccountPrefManager
import com.packy.data.local.GlobalPrefManager
import com.packy.data.model.createbox.toEntity
import com.packy.data.remote.createbox.LatterService
import com.packy.domain.model.createbox.LatterEnvelope
import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.createbox.LatterRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LatterRepositoryImp @Inject constructor(
    private val api: LatterService,
    private val prefManager: AccountPrefManager
) : LatterRepository {
    override suspend fun getLatterEnvelope(): Flow<Resource<List<LatterEnvelope>>> = flow {
        emit(Resource.Loading())
        val latterEnvelope = api.getLatterEnvelope()
        emit(latterEnvelope.map { it.toEntity() })
    }

    override suspend fun getLatterSenderReceiver(): Flow<LetterSenderReceiver?> =
        prefManager.latterSenderReceiver.getData().map { it?.toEntity() }
}