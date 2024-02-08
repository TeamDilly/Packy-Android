package com.packy.data.repository.auth

import com.packy.data.remote.auth.WithdrawService
import com.packy.domain.repository.auth.WithdrawRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WithdrawRepositoryImp @Inject constructor(
    private val api: WithdrawService
) : WithdrawRepository {
    override suspend fun withdraw(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val withdraw = api.withdraw()
        emit(withdraw.map { Unit })
    }

}