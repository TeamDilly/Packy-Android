package com.packy.domain.usecase.profile

import com.packy.domain.model.profile.ProfileDesign
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetProfilesUseCase {
    suspend fun getProfile(): Flow<Resource<List<ProfileDesign>>>
}