package com.packy.domain.repository.profile

import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileUpdateRepository {
    suspend fun updateNickName(nickname: String): Flow<Resource<Unit>>
    suspend fun updateProfile(profileId: Int): Flow<Resource<Unit>>
}