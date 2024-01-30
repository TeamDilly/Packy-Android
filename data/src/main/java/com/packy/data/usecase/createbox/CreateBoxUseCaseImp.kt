package com.packy.data.usecase.createbox

import com.packy.domain.model.createbox.box.CreateBox
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import javax.inject.Inject

class CreateBoxUseCaseImp @Inject constructor(
    private val repository: CreateBoxRepository
) : CreateBoxUseCase {
    override suspend fun getCreatedBox(): CreateBox = repository.getCreatedBox()

    override suspend fun createBox(createBox: CreateBox) {
        repository.createBox(createBox)
    }

}