package com.packy.data.repository.profile

import com.packy.data.remote.profile.ProfileService
import com.packy.domain.model.profile.ProfileDesign
import com.packy.domain.repository.profile.ProfileRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImp @Inject constructor(
    private val api: ProfileService
) : ProfileRepository {
    override suspend fun getProfile(): Flow<Resource<List<ProfileDesign>>> = flow {
        emit(Resource.Loading())
        val profileResource = api.getProfiles()
        emit(profileResource.map { list -> list.map { it.toEntity() } })
    }
}