package com.packy.domain.repository.usable

import com.packy.data.remote.usable.UsableService
import com.packy.domain.model.usable.Usable
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsableStatusRepositoryImp @Inject constructor(
    private val service: UsableService
) : UsableStatusRepository {
    override suspend fun getUsable(
        version: String,
    ): Flow<Resource<Usable>> = flow {
        emit(Resource.Loading())
        val getUsableDto = service.getUsable(version)
        emit(getUsableDto.map { it.toEntity() })
    }
}