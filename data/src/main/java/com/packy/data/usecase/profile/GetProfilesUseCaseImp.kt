package com.packy.data.usecase.profile

import com.packy.domain.model.profile.ProfileDesign
import com.packy.domain.repository.profile.ProfileRepository
import com.packy.domain.usecase.profile.GetProfilesUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilesUseCaseImp @Inject constructor(
    private val repository: ProfileRepository
) : GetProfilesUseCase {
    override suspend fun getProfile(): Flow<Resource<List<ProfileDesign>>> = repository.getProfile()
}