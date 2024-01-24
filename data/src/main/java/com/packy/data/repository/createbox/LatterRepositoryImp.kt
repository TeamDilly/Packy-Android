package com.packy.data.repository.createbox

import com.packy.data.local.AccountPrefManager
import com.packy.data.model.createbox.LetterSenderReceiverDto
import com.packy.data.model.createbox.toEntity
import com.packy.data.remote.createbox.LetterService
import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.letter.LetterRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LetterRepositoryImp @Inject constructor(
    private val api: LetterService,
    private val prefManager: AccountPrefManager
) : LetterRepository {
    override suspend fun getLetterEnvelope(): Flow<Resource<List<LetterEnvelope>>> = flow {
        emit(Resource.Loading())
        val LetterEnvelope = api.getLetterEnvelope()
        emit(LetterEnvelope.map { it.toEntity() })
    }

    override suspend fun getLetterSenderReceiver(): Flow<LetterSenderReceiver?> =
        prefManager.letterSenderReceiver.getData().map { it?.toEntity() }

    override suspend fun setLetterSenderReceiver(letterSenderReceiver: LetterSenderReceiver) {
        prefManager.letterSenderReceiver.putData(
            LetterSenderReceiverDto(
                receiver = letterSenderReceiver.receiver,
                sender = letterSenderReceiver.sender
            )
        )
    }
}