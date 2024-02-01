package com.packy.data.usecase.letter

import com.packy.domain.model.createbox.LetterSenderReceiver
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.repository.letter.LetterRepository
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLetterSenderReceiverUseCaseImp @Inject constructor(
    private val repository: LetterRepository,
    private val createBoxRepository: CreateBoxRepository
) : GetLetterSenderReceiverUseCase {
    override suspend fun getLetterSenderReceiver(): Flow<LetterSenderReceiver?> =
        repository.getLetterSenderReceiver()

    override suspend fun setLetterSenderReceiver(letterSenderReceiver: LetterSenderReceiver) {
        repository.setLetterSenderReceiver(letterSenderReceiver)
        val createdBox = createBoxRepository.getCreatedBox()
        createBoxRepository.setCreateBox(
            createdBox.copy(
                senderName = letterSenderReceiver.sender,
                receiverName = letterSenderReceiver.receiver
            )
        )
    }
}