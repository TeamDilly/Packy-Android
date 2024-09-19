package com.packy.data.remote.home

import com.packy.data.model.home.SettingDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class SettingService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getSettings(): Resource<List<SettingDto>> = safeRequest {
        httpClient.get(urlString = "/api/v2/admin/settings")
    }
}