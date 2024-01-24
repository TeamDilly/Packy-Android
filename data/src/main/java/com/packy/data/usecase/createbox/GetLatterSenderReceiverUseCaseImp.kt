package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.createbox.LatterRepository
import com.packy.domain.usecase.createbox.GetLatterSenderReceiverUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatterSenderReceiverUseCaseImp @Inject constructor(
    private val repository: LatterRepository,
) : GetLatterSenderReceiverUseCase {
    override suspend fun getLatterSenderReceiver(): Flow<LetterSenderReceiver?> =
        repository.getLatterSenderReceiver()

    override suspend fun setLatterSenderReceiver(letterSenderReceiver: LetterSenderReceiver) =
        repository.setLatterSenderReceiver(letterSenderReceiver)
}