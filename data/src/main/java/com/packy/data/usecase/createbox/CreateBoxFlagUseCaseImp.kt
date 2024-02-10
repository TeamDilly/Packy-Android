package com.packy.data.usecase.createbox

import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.usecase.createbox.CreateBoxFlagUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateBoxFlagUseCaseImp @Inject constructor(
    private val repository: CreateBoxRepository
) : CreateBoxFlagUseCase {
    override suspend fun shouldShowBoxMotion(): Flow<Boolean> = repository.shouldShowBoxMotion()
    override suspend fun shownShowBoxMotion() {
        repository.shownShowBoxMotion()
    }

    override suspend fun shouldShowBoxTutorial(): Flow<Boolean> = repository.shouldShowBoxTutorial()

    override suspend fun shownShowBoxTutorial() {
        repository.shownShowBoxTutorial()
    }

    override suspend fun shouldShowBoxSharMotion(): Flow<Boolean> =
        repository.shouldShowBoxSharMotion()

    override suspend fun shownShowBoxSharMotion() {
        repository.shownShowBoxSharMotion()
    }
}