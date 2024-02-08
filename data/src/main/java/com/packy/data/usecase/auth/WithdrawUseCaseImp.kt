package com.packy.data.usecase.auth

import com.packy.domain.repository.auth.WithdrawRepository
import com.packy.domain.usecase.auth.WithdrawUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WithdrawUseCaseImp @Inject constructor(
    private val repository: WithdrawRepository
) : WithdrawUseCase {
    override suspend fun withdraw(): Flow<Resource<Unit>> = repository.withdraw()
}