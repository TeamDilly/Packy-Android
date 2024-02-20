package com.packy.data.usecase.profile

import com.packy.domain.repository.profile.ProfileUpdateRepository
import com.packy.domain.usecase.profile.UpdateNickNameUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNickNameUseCaseImp @Inject constructor(
    private val profileUpdateRepository: ProfileUpdateRepository
): UpdateNickNameUseCase {
    override suspend fun updateNickName(nickname: String): Flow<Resource<Unit>> = profileUpdateRepository.updateNickName(nickname)
}