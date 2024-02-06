package com.packy.domain.usecase.home

import com.packy.domain.model.settings.SettingItem
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetSettingUseCase {
    suspend fun getSettings(): Flow<Resource<List<SettingItem>>>
}