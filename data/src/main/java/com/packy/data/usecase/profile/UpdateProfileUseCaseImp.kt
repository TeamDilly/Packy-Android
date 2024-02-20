package com.packy.data.usecase.profile

import com.packy.domain.repository.profile.ProfileUpdateRepository
import com.packy.domain.usecase.profile.UpdateProfileUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileUseCaseImp @Inject constructor(
    private val profileUpdateRepository: ProfileUpdateRepository
) : UpdateProfileUseCase {
    override suspend fun updateProfile(profileId: Int): Flow<Resource<Unit>> =
        profileUpdateRepository.updateProfile(profileId)
}