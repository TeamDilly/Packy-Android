package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.domain.repository.createbox.LetterRepository
import com.packy.domain.usecase.createbox.LetterUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LetterUseCaseImp @Inject constructor(
    private val LetterRepository: LetterRepository
) : LetterUseCase {
    override suspend fun getLetterEnvelope(): Flow<Resource<List<LetterEnvelope>>> =
        LetterRepository.getLetterEnvelope()
}