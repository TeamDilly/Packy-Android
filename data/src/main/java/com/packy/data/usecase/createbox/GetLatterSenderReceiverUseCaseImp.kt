package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.createbox.LetterRepository
import com.packy.domain.usecase.createbox.GetLetterSenderReceiverUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLetterSenderReceiverUseCaseImp @Inject constructor(
    private val repository: LetterRepository,
) : GetLetterSenderReceiverUseCase {
    override suspend fun getLetterSenderReceiver(): Flow<LetterSenderReceiver?> =
        repository.getLetterSenderReceiver()

    override suspend fun setLetterSenderReceiver(letterSenderReceiver: LetterSenderReceiver) =
        repository.setLetterSenderReceiver(letterSenderReceiver)
}