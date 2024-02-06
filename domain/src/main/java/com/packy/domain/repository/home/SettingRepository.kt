package com.packy.domain.repository.home

import com.packy.domain.model.settings.SettingItem
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun getSettings(): Flow<Resource<List<SettingItem>>>
}