package com.packy.data.usecase.home

import com.packy.domain.model.settings.SettingItem
import com.packy.domain.repository.home.SettingRepository
import com.packy.domain.usecase.home.GetSettingUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingUseCaseImp @Inject constructor(
    private val repository: SettingRepository
) : GetSettingUseCase {
    override suspend fun getSettings(): Flow<Resource<List<SettingItem>>> = repository.getSettings()
}