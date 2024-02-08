package com.packy.domain.repository.profile

import com.packy.domain.model.profile.ProfileDesign
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<Resource<List<ProfileDesign>>>
}