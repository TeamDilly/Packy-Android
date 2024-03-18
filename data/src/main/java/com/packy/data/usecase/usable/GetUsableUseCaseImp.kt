package com.packy.domain.usecase.usable

import com.packy.domain.model.usable.Usable
import com.packy.domain.repository.usable.UsableStatusRepository
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsableUseCaseImp @Inject constructor(
    private val repository: UsableStatusRepository
) : GetUsableUseCase {
    override suspend fun getUsable(
        version: String,
    ): Flow<Resource<Usable>> = repository.getUsable(version)
}