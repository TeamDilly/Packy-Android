package com.packy.data.usecase.home

import com.packy.domain.model.my.MyProfile
import com.packy.domain.repository.home.SettingRepository
import com.packy.domain.usecase.home.GetMyProfileUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyProfileUseCaseImp @Inject constructor(
    private val repository: SettingRepository
) : GetMyProfileUseCase {
    override suspend fun getMyProfile(): Flow<Resource<MyProfile>> = repository.getMyProfile()
}