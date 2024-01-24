package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.createbox.LatterRepository
import com.packy.domain.usecase.createbox.GetLatterSenderReceiver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatterSenderReceiverImp @Inject constructor(
    private val repository: LatterRepository
) : GetLatterSenderReceiver {
    override suspend fun getLatterSenderReceiver(): Flow<LetterSenderReceiver?> =
        repository.getLatterSenderReceiver()
}