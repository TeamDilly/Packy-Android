package com.packy.data.repository.profile

import com.packy.data.remote.profile.ProfileUpdateService
import com.packy.domain.repository.profile.ProfileUpdateRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileUpdateRepositoryImp @Inject constructor(
    private val profileUpdateService: ProfileUpdateService
) : ProfileUpdateRepository {
    override suspend fun updateNickName(nickname: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val updateResource = profileUpdateService.updateProfile(nickname = nickname, )
        emit(updateResource.map {  })
    }

    override suspend fun updateProfile(profileId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val updateResource = profileUpdateService.updateProfile(profileImg =  profileId)
        emit(updateResource.map {  })
    }
}