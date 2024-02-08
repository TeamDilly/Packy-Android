package com.packy.data.remote.profile

import com.packy.data.model.profile.ProfileDesignDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun getProfiles(): Resource<List<ProfileDesignDto>> =
        httpClient.get("/api/v1/admin/design/profiles")
            .toResource()
}