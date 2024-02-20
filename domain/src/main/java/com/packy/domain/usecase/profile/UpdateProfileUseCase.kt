package com.packy.domain.usecase.profile

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateProfileUseCase {
    suspend fun updateProfile(profileId: Int): Flow<Resource<Unit>>
}