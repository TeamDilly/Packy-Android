package com.packy.domain.usecase.home

import com.packy.domain.model.my.MyProfile
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetMyProfileUseCase {
    suspend fun getMyProfile(): Flow<Resource<MyProfile>>
}